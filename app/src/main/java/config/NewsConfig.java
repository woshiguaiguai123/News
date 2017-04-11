package config;

import java.util.ArrayList;
import java.util.List;

import entity.NewsInfo;

/**
 * Created by xcdq on 2017/2/20.
 */

public class NewsConfig {
    public static String getNewsUrl(String newsType){
        StringBuffer sb=new StringBuffer();
        sb.append("http://v.juhe.cn/toutiao/index?type=");
        sb.append(newsType);
        sb.append("&key=7066afea2bde94d7edfcefda25966b3b");
        return sb.toString();
    }
    //用于存放用户收藏的新闻
    public static List<NewsInfo> newsInfo=new ArrayList<>();
}
