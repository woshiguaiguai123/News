package adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.googl.xcdq.news.R;
import com.google.android.gms.common.images.ImageManager;

import java.util.List;

import entity.NewsInfo;
import util.CommonUtil;
import util.ImageLoader;

/**
 * Created by xcdq on 2017/2/23.
 */

public class FavoriteFragmentAdaoter extends RecyclerView.Adapter<FavoriteFragmentAdaoter.ViewHolder> {
    private List<NewsInfo> datas;
    private ViewHolder mViewHolder;
    private ImageLoader loader;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycleview_favorite, null);
        loader=new ImageLoader(parent.getContext());
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        mViewHolder = holder;
        NewsInfo data = datas.get(position);
        holder.newsPic.setImageResource(R.mipmap.ic_launcher);
        Bitmap bitmap = loader.LoaderImage(data.picUrl, l);
        if (bitmap!=null){
            holder.newsPic.setImageBitmap(bitmap);
        }
        holder.newsTitle.setText(data.title);
        holder.newsData.setText(data.date);
        holder.currentTime.setText(CommonUtil.formatTime(System.currentTimeMillis()));
    }

    private ImageLoader.OnLoaderImageListener l = new ImageLoader.OnLoaderImageListener() {
        @Override
        public void onImageLoaderOk(String url, Bitmap bitmap) {
            mViewHolder.newsPic.setImageBitmap(bitmap);

        }

        @Override
        public void onImageLoaderError(String url) {
            mViewHolder.newsPic.setImageResource(R.mipmap.ic_launcher);
        }
    };

    @Override
    public int getItemCount() {
        return datas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView newsPic;
        private TextView newsTitle, newsData, currentTime;

        public ViewHolder(View itemView) {
            super(itemView);
            newsPic = (ImageView) itemView.findViewById(R.id.iv_newslistfragment_favorite);
            newsTitle = (TextView) itemView.findViewById(R.id.tv_newsliatfragment_favorite);
            newsData = (TextView) itemView.findViewById(R.id.tv_newslistfragment_favorite);
            currentTime = (TextView) itemView.findViewById(R.id.tv_favorite_time);
        }
    }

    public void addDatasToAdapter(List<NewsInfo> newsInfo) {
        this.datas = newsInfo;
    }
}
