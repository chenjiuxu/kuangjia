package com.example.administrator.chen.Application;

import android.app.Application;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by Administrator on 2015/4/8.
 */
public class ChenApplication extends Application {
    private Handler mHandler;
    private final int MSG = 1002;
    @Override
    public void onCreate() {

        super.onCreate();
        SDKInitializer.initialize(getApplicationContext());
        JPushInterface.setDebugMode(true);//推送调试
        JPushInterface.init(this);//推送启动
        mHandler = new MyHandler();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("Alias", "陈玖旭");
        Set<String> set=new HashSet<String>();
        set.add("a");
        set.add("b");
        set.add("c");
        map.put("Tags", set);
        mHandler.sendMessage(mHandler.obtainMessage(MSG, map));//发送通知
    }
    private class MyHandler extends Handler{//设置标签别名
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            if(msg.what==MSG){
                HashMap map = (HashMap) msg.obj;
                String Alias = (String) map.get("Alias");
                Set<String> Tags = (Set<String>) map.get("Tags");
                Toast.makeText(ChenApplication.this, "这是什么", Toast.LENGTH_SHORT).show();
                JPushInterface.setAliasAndTags(ChenApplication.this, Alias, Tags, new TagAliasCallback() {
                    @Override
                    public void gotResult(int i, String s, Set<String> set) {
                        HashMap<String, Object> map = new HashMap<String, Object>();
                        map.put("Alias", s);
                        map.put("Tags", set);
                        if(i!=0){
                            mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG, map), 1000 * 60);
                            Log.e("标签", "标签别名设置失败! 一分钟后从新设置");
                        }else {
                            Log.e("标签", "标签别名设置成功!");
                        }
                    }
                });
            }
        }
    }
}
