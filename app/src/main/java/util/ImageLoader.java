package util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by xcdq on 2017/2/23.
 */
//图片加载及缓存工具类
public class ImageLoader {
    //key:根据什么去取存过的东西
    //value:你要存的东西
    private static LruCache<String, Bitmap> lruCache;
    private File path;

    public ImageLoader(Context context) {
        int size = (int) Runtime.getRuntime().maxMemory() / 8;
        lruCache = new LruCache<String, Bitmap>(size) {
            //计算每张图片的大小
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
        path = context.getExternalCacheDir();
        if (!path.exists()) {
            path.mkdirs();
        }
    }

    public Bitmap LoaderImage(String url, OnLoaderImageListener l) {
        Bitmap bitmap = null;
        //内存取
        bitmap = getFromCache(url);
        if (bitmap != null) {
            return bitmap;
        }
        //文件取
        bitmap = getFromFile(url);
        if (bitmap != null) {
            return bitmap;
        }
        //网络下载
        getFromNet(url, l);
        return null;
    }

    public interface OnLoaderImageListener {
        void onImageLoaderOk(String url, Bitmap bitmap);

        void onImageLoaderError(String url);
    }

    private void saveToCache(String url, Bitmap bitmap) {
        lruCache.put(url, bitmap);
    }

    private Bitmap getFromCache(String url) {
        return lruCache.get(url);
    }

    private void saveToFile(String url, Bitmap bitmap) {
        String fileName = url.substring(url.lastIndexOf('/') + 1);
        OutputStream os = null;
        try {
            os = new FileOutputStream(new File(path, fileName));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Bitmap getFromFile(String url) {
        String fileName = url.substring(url.lastIndexOf('/') + 1);
        File file = new File(path, fileName);
        if (file.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            if (bitmap != null) {
                saveToCache(url, bitmap);
                return bitmap;
            }
        }
        return null;
    }

    private void getFromNet(String url, OnLoaderImageListener l) {
        MyAsyncTask myAsyncTask = new MyAsyncTask(l);
        myAsyncTask.execute(url);
    }

    //Params:执行该任务需要传什么参数
    //Progress:执行该任务是否需要在界面显示进度，如果需要，传入进度的数据类型
    //Result:执行该任务需要返回的数据类型
    class MyAsyncTask extends AsyncTask<String, Void, Bitmap> {
        private String newsUrl;
        private OnLoaderImageListener l;

        public MyAsyncTask(OnLoaderImageListener l) {
            this.l = l;
        }

        //默认在子线程中执行，用于执行后台任务
        @Override
        protected Bitmap doInBackground(String... params) {
            newsUrl = params[0];
            Bitmap bitmap = doNetWork();
            return bitmap;//该返回值被传递到onPostExecute()方法内
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap != null) {
                saveToCache(newsUrl, bitmap);
                saveToFile(newsUrl, bitmap);
                l.onImageLoaderOk(newsUrl, bitmap);
            } else {
                l.onImageLoaderError(newsUrl);
            }
        }

        private Bitmap doNetWork() {
            InputStream is = null;
            try {
                URL url = new URL(newsUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setReadTimeout(20 * 1000);
                conn.setConnectTimeout(20 * 1000);
                is = conn.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                if (bitmap != null) {
                    return bitmap;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }
    }
}
