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
     * ���֪ͨ
     *
     * @param context
     * @param i       ֪ͨid
     */
    public static void clearNotice(Context context, int i) {
        if (i == 0) {
            JPushInterface.clearAllNotifications(context);//���ȫ��
        } else {
            JPushInterface.clearNotificationById(context, i);//�������
        }
    }

    /**
     * ����֪ͨ����ʱ��
     *
     * @param context
     * @param set     �趨�������ڼ�����
     * @param start   һ���м��㿪ʼ����
     * @param finish  һ���м����������
     */
    public static void PushTime(Context context, Set<Integer> set, int start, int finish) {
        JPushInterface.setPushTime(context, set, start, finish);
    }

    /**
     * ���þ�Ĭ
     *
     * @param context Ӧ�õ�ApplicationContext
     * @param i       ����ʱ�εĿ�ʼʱ�� - Сʱ ��24Сʱ�ƣ���Χ��0~23 ��
     * @param i1      ����ʱ�εĿ�ʼʱ�� - ���ӣ���Χ��0~59 ��
     * @param b       ����ʱ�εĽ���ʱ�� - Сʱ ��24Сʱ�ƣ���Χ��0~23 ��
     * @param b1      ����ʱ�εĽ���ʱ�� - ���ӣ���Χ��0~59 ��
     */
    public static void SilenceTime(Context context, int i, int i1, int b, int b1) {
        JPushInterface.setSilenceTime(context, i, i1, b, b1);
    }


    /**
     * ����֪ͨ����ʽ
     *
     * @param context
     * @param Layout  �����ļ�
     * @param icon    �����ļ���ͼƬ
     * @param title   �����ļ���title
     * @param text    �����ļ���text
     */
    public static void setDefaultPushNotificationBuilder(Context context, int Layout, int icon, int title, int text) {
        JPushInterface.setDefaultPushNotificationBuilder(new CustomPushNotificationBuilder(context, Layout, icon, title, text));
    }

    /**
     * ���ñ���֪ͨ
     *
     * @param context
     * @param test    ����
     * @param title   title
     * @param id      ����֪ͨid
     * @param year    ��
     * @param month   ��
     * @param day     ��
     * @param hour    ʱ
     * @param minute  ��
     * @param second  ��
     * @param map     map json��ֵ��
     */
    public static void LocalNotification(Context context, String test, String title, long id, int year, int month, int day, int hour, int minute, int second, Map map) {
        JPushLocalNotification ln = new JPushLocalNotification();
        ln.setBuilderId(0);//��ʽ
        ln.setContent(test);//����
        ln.setTitle(title);//����
        ln.setNotificationId(id);//֪ͨid
        ln.setBroadcastTime(year, month, day, hour, minute, second);//����ʱ��
        if (map != null) {
            JSONObject json = new JSONObject(map);//����json
            ln.setExtras(json.toString());
        }
        JPushInterface.addLocalNotification(context, ln);
    }

}
