package fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.googl.xcdq.news.R;

import adapter.FavoriteFragmentAdaoter;
import config.NewsConfig;
import entity.NewsInfo;

/**
 * Created by xcdq on 2017/2/15.
 */

public class FavoriteFragment extends Fragment {
    private View view;
    private RecyclerView mRecyclerView;
    private FavoriteFragmentAdaoter adaoter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        adaoter=new FavoriteFragmentAdaoter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_fragment_favorite, null);
        initWiuuidget();
        return view;
    }

    private void initWiuuidget() {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycleview_favorite);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adaoter.addDatasToAdapter(NewsConfig.newsInfo);
        mRecyclerView.setAdapter(adaoter);
        adaoter.notifyDataSetChanged();
    }
}
