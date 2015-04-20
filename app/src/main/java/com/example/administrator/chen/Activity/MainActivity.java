package com.example.administrator.chen.Activity;


import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;

import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapView;
import com.example.administrator.chen.Fragment.map;
import com.example.administrator.chen.Fragment.three;
import com.example.administrator.chen.R;
import com.example.administrator.chen.Fragment.Two;
import com.example.administrator.chen.Account.constant;
import com.example.administrator.chen.Fragment.one;

import java.io.IOException;

import cn.jpush.android.api.JPushInterface;

/**
 * 主界面
 */
public class MainActivity extends ActionBarActivity implements RadioGroup.OnCheckedChangeListener {
    private Toolbar toolbar;

    public Uri uri;
    private RadioGroup rg;
    private RelativeLayout al;
    private FragmentManager manager;
    private one onw = new one();
    private Two two = new Two();
    private three three = new three();
    private map map=new map();
    private ListView main_list_view;
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";
    public static boolean isForeground = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbar();
        syncDrawerAnToolbar();
        initial();
        getToken();
        System.out.println("===============" + getPackageName());
        
    }

    @Override
    protected void onResume() {
        JPushInterface.onResume(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        JPushInterface.onPause(this);
        super.onPause();
    }


    /**
     * 初始化页面控件
     */
    private void initial() {
        rg = (RadioGroup) findViewById(R.id.main_RadioGroup);
        al = (RelativeLayout) findViewById(R.id.main_RelativeLayout);
        main_list_view = (ListView) findViewById(R.id.main_list_view);
        rg.setOnCheckedChangeListener(this);
        getSupportFragmentManager().beginTransaction().add(R.id.main_RelativeLayout,
                onw).commit();
        initialLstview();

    }

    /**
     * 初始化侧滑
     */
    private void initialLstview() {

        View view = LayoutInflater.from(this).inflate(R.layout.testview, null);

        main_list_view.addHeaderView(view);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.mian_item, R.id.mian_item_tv, new String[]{"收藏", "关注", "呵呵"});
        main_list_view.setAdapter(adapter);
    }

    /**
     * 同步侧滑toolbar
     */
    private void syncDrawerAnToolbar() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                toolbar.setTitle("首页");
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                toolbar.setTitle("用户中心");
            }
        };
        drawer.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();//同步
    }

    /**
     * 设置Toolbar
     */
    private void setToolbar() {
        toolbar = (Toolbar) findViewById(R.id.tl_custom);
        toolbar.setTitle("首页");
        setSupportActionBar(toolbar);//替代ActionBar
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * 获得token值判断是否登陆
     */
    private void getToken() {
        AccountManager.get(this).getAuthTokenByFeatures(constant.ACCOUNT_TYPE, constant.TOKEN_TYPE, new String[0], this, null, null, new callback(), null);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {//RadioGroup监听器
        manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        switch (checkedId) {
            case R.id.main_rb1:
                transaction.replace(R.id.main_RelativeLayout, onw).commit();
                return;
            case R.id.main_rb2:
                transaction.replace(R.id.main_RelativeLayout, two).commit();
                return;
            case R.id.main_rb3:
                transaction.replace(R.id.main_RelativeLayout, three).commit();
                return;
            case R.id.main_rb4:
                transaction.replace(R.id.main_RelativeLayout, map).commit();
                ;


        }

    }

    private class callback implements AccountManagerCallback<Bundle> {
        @Override
        public void run(AccountManagerFuture<Bundle> future) {
            try {
                Bundle bundle = future.getResult();
                String token = bundle.getString(AccountManager.KEY_AUTHTOKEN);//获得token
                Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
            } catch (OperationCanceledException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (AuthenticatorException e) {
                e.printStackTrace();
            }
            //如果有token 跳转下一个页面
        }
    }


    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String messge = intent.getStringExtra(KEY_MESSAGE);
                String extras = intent.getStringExtra(KEY_EXTRAS);
                StringBuilder showMsg = new StringBuilder();
                showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                if (!extras.isEmpty()) {
                    showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                }
//                setCostomMsg(showMsg.toString());
            }
        }
    }

}

