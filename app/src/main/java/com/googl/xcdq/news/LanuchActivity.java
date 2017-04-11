package com.googl.xcdq.news;

import android.content.res.AssetFileDescriptor;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Surface;
import android.view.TextureView;
import android.widget.Button;

import java.io.FileDescriptor;
import java.io.IOException;

import base.BaseActivity;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LanuchActivity extends BaseActivity {
    //mediaplayer
    //1.播放什么---数据
    //2.到哪播放---surfaceView(缺点：不能平移、旋转、缩放),TextureView
    @Bind(R.id.btn_signin_lanuch)
    Button btn_signin_lanuch;
    @Bind(R.id.btn_signup_lanuch)
    Button btn_signup_lanuch;
    @Bind(R.id.textureview)
    TextureView textureView;
    private MediaPlayer player;
    private int pausePosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lanuch);
        ButterKnife.bind(this);
        textureView.setSurfaceTextureListener(l);
    }

    @OnClick(R.id.btn_signin_lanuch)
    public void singin() {
        startActivity(MainActivity.class);
        LanuchActivity.this.finish();
    }

    private TextureView.SurfaceTextureListener l = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surfacetexture, int width, int height) {
            player = new MediaPlayer();
            try {
                //拿到要播放的数据，并返回一个AssetFileDescriptor的对象
                AssetFileDescriptor afd = getAssets().openFd("welcome.mp4");
                FileDescriptor fd = afd.getFileDescriptor();
                player.setDataSource(fd, afd.getStartOffset(), afd.getLength());//设置要播放的数据
                Surface surface = new Surface(surfacetexture);
                player.setSurface(surface);
                player.setLooping(true);
                player.prepareAsync();
                //监听视频是否准备完毕
                player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        player.start();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {

        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        if (player.isPlaying()) {
            player.pause();
            pausePosition = player.getCurrentPosition();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (!player.isPlaying()) {
            player.seekTo(pausePosition);
            player.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player == null) {
            player.stop();
            player.release();
            player = null;
        }
    }
}
