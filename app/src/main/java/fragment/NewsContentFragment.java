package fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.androidadvance.topsnackbar.TSnackbar;
import com.googl.xcdq.news.R;

import config.NewsConfig;
import entity.NewsInfo;

/**
 * Created by xcdq on 2017/2/22.
 */

public class NewsContentFragment extends Fragment {
    private View view;
    private WebView mWebView;
    private String newsContentUrl;
    private NewsInfo mNewsInfo;
    private TSnackbar mTSnackbar;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mNewsInfo = getArguments().getParcelable("newsContentUrl");
        newsContentUrl=mNewsInfo.url;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layput_fragment_newscontent, null);
        initwidget();
        collectNews();
        return view;
    }

    public void initwidget() {
        mWebView = (WebView) view.findViewById(R.id.wv_newscontent_fragment);
        //设置mWebView是否支持JavaScript语言
        mWebView.getSettings().setJavaScriptEnabled(true);
        //设置网页在当前WebView上显示而不是第三方浏览器
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.loadUrl(newsContentUrl);
    }

    public void collectNews() {
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.add) {
                    if (NewsConfig.newsInfo.size() <= 0) {
                        NewsConfig.newsInfo.add(mNewsInfo);
                        mTSnackbar= TSnackbar.make(view.findViewById(R.id.coor),"收藏成功",TSnackbar.LENGTH_SHORT);
                        View view=mTSnackbar.getView();
                        view.setBackgroundColor(Color.parseColor("#FFFFFF"));
                        mTSnackbar.show();
//                      Toast.makeText(getActivity(), "收藏成功", Toast.LENGTH_SHORT).show();
                    } else {
                        boolean b = false;
                        for (NewsInfo data : NewsConfig.newsInfo) {
                            if (data.url.equals(mNewsInfo.url)) {
                                b = true;
                                NewsConfig.newsInfo.add(mNewsInfo);
                                mTSnackbar= TSnackbar.make(view.findViewById(R.id.coor),"收藏过了",TSnackbar.LENGTH_SHORT);
                                View view=mTSnackbar.getView();
                                view.setBackgroundColor(Color.parseColor("#FFFFFF"));
                                mTSnackbar.show();
//                              Toast.makeText(getActivity(), "收藏过了", Toast.LENGTH_SHORT).show();
                            }

                        }
                        if (b == false) {
                            NewsConfig.newsInfo.add(mNewsInfo);
                            NewsConfig.newsInfo.add(mNewsInfo);
                            mTSnackbar= TSnackbar.make(view.findViewById(R.id.coor),"收藏成功",TSnackbar.LENGTH_SHORT);
                            View view=mTSnackbar.getView();
                            view.setBackgroundColor(Color.parseColor("#FFFFFF"));
                            mTSnackbar.show();
//                          Toast.makeText(getActivity(), "收藏成功", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                return true;
            }
        });
    }
}
