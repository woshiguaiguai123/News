package fragment;

import android.os.Bundle;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.googl.xcdq.news.MainActivity;
import com.googl.xcdq.news.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import adapter.NewsListFragmentAdapter;
import config.NewsConfig;
import entity.NewsInfo;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by xcdq on 2017/2/20.
 */

public class NewsListFragment extends Fragment {
    private View view;
    private String mType;
    private RecyclerView mRecyclerView;
    private TextView tv_result;
    private String newsUrl;
    private String jsonData;//网络请求返回的Json数据
    //private List<NewsInfo> datas;//解析json数据后的数据集合
    private List<NewsInfo> newsDatas;
    private NewsListFragmentAdapter adapter;

    private android.os.Handler handler=new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==1){
                newsDatas= (List<NewsInfo>) msg.obj;
//               adapter=new NewsListFragmentAdapter();
                adapter.addDatasToAdapter(newsDatas);
                mRecyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layput_fragment_newslist, null);
        mType = getArguments().getString("type");
        initWidgeet();
        return view;
        // TODO: 2017/3/1 你妈炸了
    }

    private void initWidgeet() {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_newslist);
        LinearLayoutManager manager=new LinearLayoutManager(getActivity());
//        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
//        tv_result = (TextView) view.findViewById(R.id.tv_httpResult);
//      doHttp();
        adapter=new NewsListFragmentAdapter();
        adapter.setOnRCVItemListener(new NewsListFragmentAdapter.OnRCVItemListener() {
            @Override
                //public void onItemClick(String url) {
                public void onItemClick(NewsInfo data) {
                //跳转或切换Fragment
                NewsContentFragment f=new NewsContentFragment();
                Bundle bundle=new Bundle();
//                bundle.putString("newsContentUrl",url);
                bundle.putParcelable("newsContentUrl",data);
                f.setArguments(bundle);//给NewsContentFragment携带一些数据
                ((MainActivity)getActivity()).changFragment(f);
            }
        });

        res();
    }

    public void res() {
        newsUrl = NewsConfig.getNewsUrl(mType);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //1.初始化OkHttpClient
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .get().url(newsUrl).build();
                    //new一个Call对象，需要传入Reques请求对象，然后执行这个请求，拿到返回给我们的Response响应消息
                    Response response = client.newCall(request).execute();
                    //判断请求是否成功
                    if (response.isSuccessful()) {
                        jsonData = response.body().string();
                        Log.d("News", jsonData);
                        List<NewsInfo> datas = jsonParse(jsonData);

                        Message msg=new Message();
                        msg.what=1;
                        msg.obj=datas;
                        handler.sendMessage(msg);

                        for (NewsInfo info : datas) {
                            Log.d("new", "----------------" + info.title);
                            Log.d("new", "------------" + info.date);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //json解析
    public List<NewsInfo> jsonParse(String jsonData) {
        List<NewsInfo> newsDatas = new ArrayList<>();
        try {
            JSONObject object1 = new JSONObject(jsonData);
            if (object1.getString("reason").equals("成功的返回")) {
                JSONObject object2 = object1.getJSONObject("result");
                JSONArray array = object2.getJSONArray("data");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject o = array.getJSONObject(i);
                    NewsInfo info = new NewsInfo();
                    info.title = o.getString("title");
                    info.date = o.getString("date");
                    info.category = o.getString("category");
                    info.author_name = o.getString("author_name");
                    info.url = o.getString("url");
                    info.picUrl = o.getString("thumbnail_pic_s");
                    newsDatas.add(info);
                }
                return newsDatas;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


//从网上获取数据
//    private void doHttp() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                String newsUrl = NewsConfig.getNewsUrl(mType);
//                HttpURLConnection conn = null;
//                InputStream is = null;
//                try {
//                    URL url = new URL(newsUrl);
//                    conn = (HttpURLConnection) url.openConnection();
//                    conn.setRequestMethod("GET");
//                    conn.setConnectTimeout(5000);
//                    conn.setReadTimeout(5000);
//                    conn.connect();
//                    conn.getInputStream();
//                    final StringBuffer sb;
//                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                        is = conn.getInputStream();
//                        BufferedReader br = new BufferedReader(new InputStreamReader(is));
//                        sb = new StringBuffer();
//                        String line;
//                        while ((line = br.readLine()) != null) {
//                            sb.append(line);
//                        }
//                        getActivity().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                tv_result.setText(sb.toString());
//                            }
//                        });
//                    }
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } finally {
//                    try {
//                        if (is != null) {
//                            is.close();
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    conn.disconnect();
//                }
//            }
//        }).start();
//    }
}
