package com.googl.xcdq.news;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xcdq on 2017/2/20.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHoider> {

    private List<JavaBean> datas;

    static class ViewHoider extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;

        //View是指RecyclerView内item的最外层布局
        public ViewHoider(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.imageView);
            textView = (TextView) view.findViewById(R.id.textView);
        }
    }

    //主要用于初始化数据
    @Override
    public ViewHoider onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycler, null);
        ViewHoider viewHoider = new ViewHoider(view);
        return viewHoider;
    }

    //主要用于设置数据
    @Override
    public void onBindViewHolder(ViewHoider holder, int position) {
        JavaBean data = datas.get(position);
        holder.imageView.setImageBitmap(data.imageView);
        holder.textView.setText(data.title);
    }

    //获得item的数量
    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void addDatas(List<JavaBean> datas) {
        this.datas = datas;
    }

}
