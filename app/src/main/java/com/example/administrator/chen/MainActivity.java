package com.example.administrator.chen;


import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;

import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.IOException;


public class MainActivity extends ActionBarActivity implements RadioGroup.OnCheckedChangeListener {
    private Toolbar toolbar;

    public Uri uri;
    private RadioGroup rg;
    private RelativeLayout al;
    private FragmentManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbar();
        syncDrawerAnToolbar();
        initial();
        getToken();

    }

    /**
     * 初始化页面控件
     */
    private void initial() {
        rg = (RadioGroup) findViewById(R.id.main_RadioGroup);
        al = (RelativeLayout) findViewById(R.id.main_RelativeLayout);
        rg.setOnCheckedChangeListener(this);
        rg.check(R.id.main_rb1);

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
        manager=getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        switch (checkedId) {
            case R.id.main_rb1:
               transaction.add(R.id.main_RelativeLayout, new one(),null).commit();

            case R.id.main_rb2:
                ;
            case R.id.main_rb3:
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

}

