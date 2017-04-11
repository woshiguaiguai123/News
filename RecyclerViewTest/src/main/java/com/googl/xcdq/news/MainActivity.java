package com.googl.xcdq.news;

import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private List<JavaBean> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.reyrclerview);

//        LinearLayoutManager manager=new LinearLayoutManager(this);
        //manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(new GridLayoutManager(this,5,GridLayoutManager.VERTICAL,false));

//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));

        adapter = new MyAdapter();
        initData();
        adapter.addDatas(datas);
        recyclerView.setAdapter(adapter);
    }

    private void initData(){
        datas=new ArrayList<>();
        for (int i=0;i<20;i++){
            JavaBean data=new JavaBean();
            data.imageView= BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
            data.title="naihxiknsahisajads";
            datas.add(data);
        }
    }
}
