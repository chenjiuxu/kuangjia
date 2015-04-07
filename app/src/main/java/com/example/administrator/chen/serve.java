package com.example.administrator.chen;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by Administrator on 2015/3/31.
 */
public class serve extends Service{
    private Authenticator authenticator;

    @Override
    public IBinder onBind(Intent intent) {//Service 注册到系统。。。。。同步Z！！！！！
        Log.i("MEIZU", intent.getAction());
        return authenticator.getIBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("tag","有没有！");
        authenticator=new Authenticator(this);
        /**同步每天有学啊！*/

    }
}
