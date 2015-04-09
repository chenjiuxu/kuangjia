package com.example.administrator.chen.Account;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.administrator.chen.Account.Authenticator;

/**登陆service 同步代码
 * Created by Administrator on 2015/3/31.
 */
public class serve extends Service{
    private Authenticator authenticator;

    @Override
    public IBinder onBind(Intent intent) {//Service 注册到系统。。。。。同步Z！！！！！
        return authenticator.getIBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("tag","有没有！");
        authenticator=new Authenticator(this);
        /**同步没有学啊！*/

    }
}
