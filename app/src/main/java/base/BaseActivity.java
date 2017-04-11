package base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import util.LogUtil;

/**
 * Created by xcdq on 2017/2/13.
 */

public class BaseActivity extends AppCompatActivity {
    // 1.新建一个数组，把Activity放入数组（该集合必须是静态的，只能被掉用一次）。
    private static ArrayList<BaseActivity> activitys = new ArrayList<BaseActivity>();

    // 2.创建一个方法，来便利该数组(一键退出)。
    public void finishAll() {
        for (int i = 0; i < activitys.size(); i++) {
            BaseActivity a = activitys.get(i);
            // 3.得到一个活动，结束一个。
            a.finish();
        }
    }

    // -----------------------------Activity的跳转--------------------------------------------
    public void startActivity(Class<?> targetClass) {
        Intent intent = new Intent(this, targetClass);
        startActivity(intent);


    }

    public void startActivity(Class<?> targetClass, Bundle bundle) {
        Intent intent = new Intent(this, targetClass);
        intent.putExtras(bundle);
        startActivity(intent);


    }

    public void startActivity(Class<?> targetClass, int enterAnim, int exitAnim) {
        Intent intent = new Intent(this, targetClass);
        startActivity(intent);
        overridePendingTransition(enterAnim, exitAnim);

    }

    public void startActivity(Class<?> targetClass, Bundle bundle,
                              int enterAnim, int exitAnim) {
        Intent intent = new Intent(this, targetClass);
        intent.putExtras(bundle);
        startActivity(intent);
        overridePendingTransition(enterAnim, exitAnim);

    }

    // @Override
    // public void finish() {
    // // TODO Auto-generated method stub
    // super.finish();
    // }

    // -----------------------------管理生命周期--------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitys.add(this);
        LogUtil.d(this, "----------onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.d(this, "----------onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.d(this, "----------onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.d(this, "----------onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.d(this, "----------onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.d(this, "----------onDestroy");
        activitys.remove(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtil.d(this, "----------onRestart");
    }
}
