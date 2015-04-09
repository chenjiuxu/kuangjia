package com.example.administrator.chen.Application;

import android.app.Application;
import android.widget.Toast;

import java.util.HashSet;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2015/4/8.
 */
public class ChenApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "APP运行", Toast.LENGTH_SHORT).show();
        JPushInterface.setDebugMode(true);//推送调试
        JPushInterface.init(this);//推送启动
        HashSet<String> set = new HashSet<String>();//推送标签
        set.add("a");
        set.add("b");
        set.add("c");
        JPushInterface.setAliasAndTags(this,"陈玖旭",set);

        


    }
}
