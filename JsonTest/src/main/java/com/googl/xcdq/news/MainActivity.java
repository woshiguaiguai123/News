package com.googl.xcdq.news;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
//
//    //json数组[{'name':'zhangsang','age':'20'},{'name':'zhang','age':'30'}]
//    private void jsonTest2(){
//        String jsonData="[{'name':'zhangsang','age':'20'},{'name':'zhang','age':'30'}]";
//        JSONArray array=new JSONArray(jsonData);
//        for (int i=0;i<array.length();i++){
//            JSONObject o=array.getJSONObject(i);
//            String name=o.getString("name");
//            String age=o.getString("age");
//        }
//    }
}
