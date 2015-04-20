package com.example.administrator.chen.PushMessage;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.Log;

import org.json.JSONObject;

import java.util.Map;
import java.util.Set;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.CustomPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import cn.jpush.android.data.JPushLocalNotification;

/**
 * Created by Administrator on 2015/4/9.
 */
public class pushSet {
    /**
     * 清除通知
     *
     * @param context
     * @param i       通知id
     */
    public static void clearNotice(Context context, int i) {
        if (i == 0) {
            JPushInterface.clearAllNotifications(context);//清除全部
        } else {
            JPushInterface.clearNotificationById(context, i);//单个清除
        }
    }

    /**
     * 设置通知接收时间
     *
     * @param context
     * @param set     设定在在星期几接收
     * @param start   一天中几点开始接收
     * @param finish  一天中几点结束接收
     */
    public static void PushTime(Context context, Set<Integer> set, int start, int finish) {
        JPushInterface.setPushTime(context, set, start, finish);
    }

    /**
     * 设置静默
     *
     * @param context 应用的ApplicationContext
     * @param i       静音时段的开始时间 - 小时 （24小时制，范围：0~23 ）
     * @param i1      静音时段的开始时间 - 分钟（范围：0~59 ）
     * @param b       静音时段的结束时间 - 小时 （24小时制，范围：0~23 ）
     * @param b1      静音时段的结束时间 - 分钟（范围：0~59 ）
     */
    public static void SilenceTime(Context context, int i, int i1, int b, int b1) {
        JPushInterface.setSilenceTime(context, i, i1, b, b1);
    }


    /**
     * 设置通知栏样式
     *
     * @param context
     * @param Layout  布局文件
     * @param icon    布局文件的图片
     * @param title   布局文件的title
     * @param text    布局文件的text
     */
    public static void setDefaultPushNotificationBuilder(Context context, int Layout, int icon, int title, int text) {
        JPushInterface.setDefaultPushNotificationBuilder(new CustomPushNotificationBuilder(context, Layout, icon, title, text));
    }

    /**
     * 设置本地通知
     *
     * @param context
     * @param test    类容
     * @param title   title
     * @param id      本地通知id
     * @param year    年
     * @param month   月
     * @param day     日
     * @param hour    时
     * @param minute  分
     * @param second  秒
     * @param map     map json键值对
     */
    public static void LocalNotification(Context context, String test, String title, long id, int year, int month, int day, int hour, int minute, int second, Map map) {
        JPushLocalNotification ln = new JPushLocalNotification();
        ln.setBuilderId(0);//样式
        ln.setContent(test);//内容
        ln.setTitle(title);//标题
        ln.setNotificationId(id);//通知id
        ln.setBroadcastTime(year, month, day, hour, minute, second);//触发时间
        if (map != null) {
            JSONObject json = new JSONObject(map);//附带json
            ln.setExtras(json.toString());
        }
        JPushInterface.addLocalNotification(context, ln);
    }

}
