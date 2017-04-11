package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.googl.xcdq.news.R;

import java.util.List;

import entity.NewsInfo;
import util.ImageLoader;

/**
 * Created by xcdq on 2017/2/21.
 */

public class NewsListFragmentAdapter extends RecyclerView.Adapter<NewsListFragmentAdapter.ViewHolder> {
    private List<NewsInfo> mdatas;
    private OnRCVItemListener listener;//申明一个监听器
    private ImageLoader imageLoader;
    private ViewHolder mViewHolder;

    //点击RecyclerView的item的监听器
    public interface OnRCVItemListener {
        //void onItemClick(String url);//将网址传过来
        void onItemClick(NewsInfo data);
    }

    public void setOnRCVItemListener(OnRCVItemListener l) {
        this.listener = l;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        imageLoader = new ImageLoader(parent.getContext());
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_newlistfragment, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        //设置新闻标题的监听事件
        viewHolder.news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //让别人知道我点击了这个View
                if (listener != null) {
                    //获取当前item的位置
                    int position = viewHolder.getAdapterPosition();
                    //获取当前position的url对象
                    //String newUrl = mdatas.get(position).url;
                    NewsInfo data=mdatas.get(position);
//                    listener.onItemClick(newUrl);
                    listener.onItemClick(data);
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        mViewHolder=holder;

        NewsInfo data = mdatas.get(position);
        holder.imageView.setImageResource(R.mipmap.ic_launcher);

        //设置图片
        Bitmap bitmap = imageLoader.LoaderImage(data.picUrl, l);
        if (bitmap!=null) {
            holder.imageView.setImageBitmap(bitmap);
        }
        holder.tv_title.setText(data.title);
        holder.tv_neiyong.setText(data.date);
    }

    private ImageLoader.OnLoaderImageListener l = new ImageLoader.OnLoaderImageListener() {
        @Override
        public void onImageLoaderOk(String url, Bitmap bitmap) {
            mViewHolder.imageView.setImageBitmap(bitmap);
        }

        @Override
        public void onImageLoaderError(String url) {
            mViewHolder.imageView.setImageResource(R.mipmap.ic_launcher);
        }
    };

    @Override
    public int getItemCount() {
        return mdatas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private View news;
        private ImageView imageView;
        private TextView tv_title, tv_neiyong;

        public ViewHolder(View itemView) {
            super(itemView);
            news = itemView;
            imageView = (ImageView) itemView.findViewById(R.id.iv_newslistfragment_src);
            tv_title = (TextView) itemView.findViewById(R.id.tv_newsliatfragment_title);
            tv_neiyong = (TextView) itemView.findViewById(R.id.tv_newslistfragment_neiyong);
        }
    }

    public void addDatasToAdapter(List<NewsInfo> datas) {
        this.mdatas = datas;
    }

}
