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

/**
 * Created by Administrator on 2015/3/31.
 */
public class LoginActivity extends ActionBarActivity {
    private AccountManager manager=AccountManager.get(this);
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getToken();
            }
        });
    }
    private void getToken() {
        Intent intent1 = getIntent();
        String loginname = intent1.getStringExtra("loginname");
        String logintype = intent1.getStringExtra("logintype");
        Account account = new Account(loginname, "陈玖旭");
        if (!TextUtils.isEmpty(loginname)) {
            manager.addAccountExplicitly(account,"123456789",null);
            manager.setAuthToken(account,"testToken","accounttest");
        }else {
            manager.setPassword(account,"123456789");
        }
        Intent intent=new Intent();
        intent.putExtra(AccountManager.KEY_ACCOUNT_NAME, loginname);
        intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, "accounttest");
        if(logintype!=null&&loginname.equals("testToken")){
            intent.putExtra(AccountManager.KEY_AUTHTOKEN,"accounttest");
        }
        setResult(RESULT_OK, intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id=item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id==R.id.action_settings){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
