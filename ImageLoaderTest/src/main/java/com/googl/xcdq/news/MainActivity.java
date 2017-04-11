package com.googl.xcdq.news;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button btn;
    private ImageView iv;
    private ImageLoader imageLoader;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        url = "https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/" +
                "superman/img/logo/bd_logo1_31bdc765.png";

        imageLoader = new ImageLoader(this);
        btn = (Button) findViewById(R.id.button);
        iv = (ImageView) findViewById(R.id.imageView);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap=imageLoader.LoaderImage(url, l);
                iv.setImageBitmap(bitmap);
            }
        });
    }

    private ImageLoader.OnLoaderImageListener l = new ImageLoader.OnLoaderImageListener() {
        @Override
        public void onImageLoaderOk(String url, Bitmap bitmap) {
            iv.setImageBitmap(bitmap);
        }

        @Override
        public void onImageLoaderError(String url) {
            Toast.makeText(MainActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
        }
    };
}
