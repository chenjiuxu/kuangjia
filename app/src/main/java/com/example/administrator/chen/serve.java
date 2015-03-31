package com.example.administrator.chen;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by Administrator on 2015/3/31.
 */
public class serve extends Service{
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
