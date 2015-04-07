package com.example.administrator.chen;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;

/**
 * Created by Administrator on 2015/3/31.
 */
public class LoginActivity extends ActionBarActivity implements View.OnClickListener {
    private AccountManager manager;

    private View bt;
    private EditText et_name;
    private EditText et_password;
    private String loginname;
    private String logintype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialVIew();
        initial();
    }

    /**
     * 初始化
     */
    private void initial() {
        manager = AccountManager.get(this);
        Intent intent1 = getIntent();
        loginname = intent1.getStringExtra("loginname");
        logintype = intent1.getStringExtra("logintype");
    }

    /**
     * 初始化view
     */
    private void initialVIew() {
        et_name = (EditText) findViewById(R.id.login_et1);
        et_password = (EditText) findViewById(R.id.login_et2);
        if(TextUtils.isEmpty(loginname)){
            et_name.setText(loginname);
        }
        bt = findViewById(R.id.login_bt);
        bt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String name = et_name.getText().toString();
        String password = et_password.getText().toString();
        if (name == null || password == null) {
            Toast.makeText(this, "密码内容不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        setToken(name, password);

    }

    /**
     * 设置登录
     */
    private void setToken(String name, String password) {

        Account account = new Account("陈玖旭", constant.ACCOUNT_TYPE);//用户name ACCOUNT类型
        if (TextUtils.isEmpty(loginname)) {
            manager.addAccountExplicitly(account,null, null);//向系统添加账户
            manager.setAuthToken(account, constant.TOKEN_TYPE, "这是测试的Token");

            //设置自动同步(目前关闭)
            // 通知系统,此账户支持同步
            //ContentResolver.setIsSyncable(account, FeedContract.CONTENT_AUTHORITY, 1);
            // 通知系统,这个帐户可以获得汽车网络的同步
            //ContentResolver.setSyncAutomatically(account, FeedContract.CONTENT_AUTHORITY, true);
            // 　推荐一个时间表自动同步。系统可能会修改这个基础　
            // 　其他计划同步和网络利用率。
            //ContentResolver.addPeriodicSync(account, FeedContract.CONTENT_AUTHORITY,new Bundle(), SYNC_FREQUENCY);

        } else {
            manager.setPassword(account, null);
        }
        /////////////可以没有不知道为什么
        Intent intent = new Intent();
        intent.putExtra(AccountManager.KEY_ACCOUNT_NAME, "陈玖旭");
        intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, constant.ACCOUNT_TYPE);
        if (logintype != null && loginname.equals("testToken")) {
            intent.putExtra(AccountManager.KEY_AUTHTOKEN, "这是测试的Token");
        }
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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


}
