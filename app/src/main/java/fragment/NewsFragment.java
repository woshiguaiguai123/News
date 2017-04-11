package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.googl.xcdq.news.R;

import java.util.ArrayList;
import java.util.List;

import adapter.NewsFragmentAdapter;
import butterknife.Bind;

/**
 * Created by xcdq on 2017/2/15.
 */

public class NewsFragment extends Fragment {
    private View view;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<String> mTitles;//tab标签里要显示的文字
    private List<Fragment> mFragment;//ViewPager内Fragment的容器
    private String[] mTygpes = {"top", "shehui", "guonei", "guoji", "yule",
            "tiyu", "junshi", "keji", "caijing", "shishang"};
    private NewsFragmentAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_fragment_news, null);
        initwidget();
        initData();
        return view;
    }

    //初始化控件
    public void initwidget() {
        mTabLayout = (TabLayout) view.findViewById(R.id.tablayout_newsfragment);
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager_newsfragment);
    }

    private void initData() {
        mTitles = new ArrayList<>();
        mTitles.add("头条");
        mTitles.add("社会");
        mTitles.add("国内");
        mTitles.add("国际");
        mTitles.add("娱乐");
        mTitles.add("体育");
        mTitles.add("军事");
        mTitles.add("科技");
        mTitles.add("财经");
        mTitles.add("时尚");

        mFragment = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            NewsListFragment fragment = new NewsListFragment();
            Bundle bundle = new Bundle();
            bundle.putString("type", mTygpes[i]);
            fragment.setArguments(bundle);
            mFragment.add(fragment);
        }

        mAdapter = new NewsFragmentAdapter(getFragmentManager());
        mAdapter.addmtitles(mTitles);
        mAdapter.addFragment(mFragment);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);//把TabLayout和ViewPager关联上
    }

}
