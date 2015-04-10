package com.example.administrator.chen.PushMessage;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.chen.Account.LoginActivity;
import com.example.administrator.chen.Activity.MainActivity;

import cn.jpush.android.api.JPushInterface;

/**
 * 推送给予极光
 * 自定义接收器
 * <p/>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Toast.makeText(context, "有反应", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            Toast.makeText(context, "1", Toast.LENGTH_SHORT).show();
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {//自定义消息不会提示
            String content = bundle.getString(JPushInterface.EXTRA_MESSAGE);//拿到自定义类容
            String json = bundle.getString(JPushInterface.EXTRA_EXTRA);//拿到自定义类容
            Toast.makeText(context, content+"收到自定义消息"+json, Toast.LENGTH_SHORT).show();

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {//接受通知时运行
            String notifactionId = bundle.getString(JPushInterface.EXTRA_ALERT);//获得通知内容
            Toast.makeText(context, notifactionId+"刚刚搜到通知", Toast.LENGTH_SHORT).show();

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {//再通知栏点击了
            String notifactionId = bundle.getString(JPushInterface.EXTRA_ALERT);//获得通知内容
            Toast.makeText(context, notifactionId+"我点击了", Toast.LENGTH_SHORT).show();
            Intent i=new Intent(context,LoginActivity.class);
            i.putExtras(bundle);
            //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(i);

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Toast.makeText(context, "5", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {//每次打开软件是都可以运行
            Toast.makeText(context, "6", Toast.LENGTH_SHORT).show();
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Log.w(TAG, "[MyReceiver]" + intent.getAction() + " 连接状态改变 " + connected);
        } else {
            Toast.makeText(context, "7", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

    //send msg to MainActivity
    private void processCustomMessage(Context context, Bundle bundle) {
        if (MainActivity.isForeground) {
            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
            Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
            msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
            if (!extras.isEmpty()) {
                try {
                    JSONObject extraJson = new JSONObject(extras);
                    Log.e("是什么", extraJson.toString());
                    if (null != extraJson && extraJson.length() > 0) {
                        msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
                    }
                } catch (JSONException e) {

                }

            }
            context.sendBroadcast(msgIntent);
        }
    }
}
