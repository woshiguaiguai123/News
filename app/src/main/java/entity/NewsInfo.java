package entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by xcdq on 2017/2/20.
 */

public class NewsInfo implements Parcelable {
    public String picUrl;//新闻图片的网址
    public String title;//新闻标题
    public String date;//日期
    public String category;//新闻类别
    public String author_name;//作者
    public String url;//新闻内容的网址

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(picUrl);
        dest.writeString(title);
        dest.writeString(date);
        dest.writeString(category);
        dest.writeString(author_name);
        dest.writeString(url);
    }

    public static final Parcelable.Creator<NewsInfo> CREATOR = new Creator<NewsInfo>() {
        @Override
        public NewsInfo createFromParcel(Parcel source) {
            NewsInfo data = new NewsInfo();
            data.picUrl = source.readString();
            data.title = source.readString();
            data.date = source.readString();
            data.category = source.readString();
            data.author_name = source.readString();
            data.url = source.readString();
            return data;
        }

        @Override
        public NewsInfo[] newArray(int size) {
            return new NewsInfo[size];
        }
    };


}
