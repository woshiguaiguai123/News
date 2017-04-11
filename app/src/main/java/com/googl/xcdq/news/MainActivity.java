package com.googl.xcdq.news;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;

import com.androidadvance.topsnackbar.TSnackbar;

import base.BaseActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import de.hdodenhof.circleimageview.CircleImageView;
import fragment.CommentFragment;
import fragment.FavoriteFragment;
import fragment.LocalFragment;
import fragment.NewsContentFragment;
import fragment.NewsFragment;
import fragment.PhotoFragment;
import util.CameraAlbumUtil;
import util.PermissionUtil;

public class MainActivity extends BaseActivity {
    private CommentFragment commentFragment;
    private FavoriteFragment favoriteFragment;
    private NewsFragment newsFragment;
    private LocalFragment localFragment;
    private PhotoFragment photoFragment;
    private CircleImageView iv_headPic;
    private CameraAlbumUtil cameraAlbumUtil;
    private TSnackbar mTSnackbar;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.activity_main)
    DrawerLayout drawerLayout;
    @Bind(R.id.nav)
    NavigationView navigationView;
    //DrawerLayout:抽屉布局
    //NavigationView:侧滑菜单---menu+headerLayout

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initToolBar();
        initNavigation();
        initHeaderLatoyt();
        changFragment(new NewsFragment());
        cameraAlbumUtil = new CameraAlbumUtil(this);
        ShareSDK.initSDK(this);
        JPushInterface.setDebugMode(true);
        init();
    }

    // 初始化 JPush。如果已经初始化，但没有登录成功，则执行重新登录。
    private void init() {
        JPushInterface.init(getApplicationContext());
    }

    private void initToolBar() {

//        //设置toolbar高度和内边距
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            toolbar.getLayoutParams().height = getAppBarHeight();
//            toolbar.setPadding(toolbar.getPaddingLeft(),
//                    getStatusBarHeight(),
//                    toolbar.getPaddingRight(),
//                    toolbar.getPaddingBottom());
//        }

        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        //设置DrawableLayout点击图标
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_launcher);
    }

    private void initNavigation() {
        navigationView.setCheckedItem(R.id.nav_news);//头条设置的灰色，一旦进去之后自己就默认为灰色
        //设置DrawableLayout点击监听
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_comment:
                        if (commentFragment == null) {
                            commentFragment = new CommentFragment();
                        }
                        changFragment(commentFragment);
                        drawerLayout.closeDrawers();//一旦点击以后就会关闭菜单栏
                        break;
                    case R.id.nav_favorite:
                        if (favoriteFragment == null) {
                            favoriteFragment = new FavoriteFragment();
                        }
                        changFragment(favoriteFragment);
                        drawerLayout.closeDrawers();//一旦点击以后就会关闭菜单栏
                        break;
                    case R.id.nav_local:
                        if (localFragment == null) {
                            localFragment = new LocalFragment();
                        }
                        changFragment(localFragment);
                        drawerLayout.closeDrawers();//一旦点击以后就会关闭菜单栏
                        break;
                    case R.id.nav_news:
                        if (newsFragment == null) {
                            newsFragment = new NewsFragment();
                        }
                        changFragment(newsFragment);
                        drawerLayout.closeDrawers();//一旦点击以后就会关闭菜单栏
                        break;
                    case R.id.nav_photo:
                        if (photoFragment == null) {
                            photoFragment = new PhotoFragment();
                        }
                        changFragment(photoFragment);
                        drawerLayout.closeDrawers();//一旦点击以后就会关闭菜单栏
                        break;
                }
                return true;
            }
        });
    }

    //设置Fragment
    public void changFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        //判断是否是新闻界面的Fragment(如果传入的Fragment是NewsContentFragment，就将它放入返回栈)
        if (fragment instanceof NewsContentFragment) {
            transaction.addToBackStack(null);
        }
        transaction.replace(R.id.fl_commcent_main, fragment);
        transaction.commit();
    }

    //设置ToolBar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    //设置ToolBar监听
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                mTSnackbar = TSnackbar.make(findViewById(android.R.id.content), "正在分享", TSnackbar.LENGTH_SHORT);
                View view = mTSnackbar.getView();
                view.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mTSnackbar.show();
                showShare();
                break;
            case R.id.add:
                mTSnackbar = TSnackbar.make(findViewById(android.R.id.content), "请到新闻内容界面收藏新闻", TSnackbar.LENGTH_SHORT);
                View view1 = mTSnackbar.getView();
                view1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                mTSnackbar.show();
                break;
            case android.R.id.home:
                drawerLayout.openDrawer(Gravity.LEFT);
                break;
        }
        return true;
    }

    //初始化控件
    private void initHeaderLatoyt() {
        //1.先找到头布局
        View headerLatoyt = navigationView.getHeaderView(0);
        //2.用头布局对象找ID
        iv_headPic = (CircleImageView) headerLatoyt.findViewById(R.id.iv_cv);
        iv_headPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDialog();
            }
        });
    }

    //初始化对话框
    private void chooseDialog() {
        new AlertDialog.Builder(this)
                .setTitle("选择头像")
                .setPositiveButton("相机", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PermissionUtil.requestPermissions(MainActivity.this, 111, new String[]{
                                Manifest.permission.CAMERA}, new PermissionUtil.OnRequestPermissionListener() {

                            @Override
                            public void onRequestGranted() {
                                cameraAlbumUtil.takePhoto();
                            }

                            @Override
                            public void onRequestDenied() {
                                Toast.makeText(MainActivity.this, "权限被拒绝", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                })
                .setNegativeButton("相册", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PermissionUtil.requestPermissions(MainActivity.this, 111, new String[]{
                                Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionUtil.OnRequestPermissionListener() {
                            @Override
                            public void onRequestGranted() {
                                cameraAlbumUtil.openAlbum();
                            }

                            @Override
                            public void onRequestDenied() {
                                Toast.makeText(MainActivity.this, "权限被拒绝", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionUtil.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bitmap = CameraAlbumUtil.onActivityResult(requestCode, resultCode, data);
        if (bitmap != null) {
            iv_headPic.setImageBitmap(bitmap);
        }
    }

    //获得状态栏高度
    private int getAppBarHeight() {
        return dip2px(56) + getStatusBarHeight();
    }

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private int dip2px(float dipValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");
        // 启动分享GUI
        oks.show(MainActivity.this);
    }

}