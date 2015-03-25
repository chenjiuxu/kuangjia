package com.example.administrator.chen;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    private Toolbar toolbar;
    private EditText ed1;
    private EditText ed2;
    private EditText ed3;
    private ContentResolver cr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ed1 = (EditText) findViewById(R.id.editText1);
        ed2 = (EditText) findViewById(R.id.editText2);
        ed3 = (EditText) findViewById(R.id.editTex3);
        View bt = findViewById(R.id.button);


        /////数据库的建立
        toolbar = (Toolbar) findViewById(R.id.tl_custom);
        toolbar.setTitle("首页");
        setSupportActionBar(toolbar);//替代ActionBar
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        actionBarDrawerToggle.syncState();
        cr = getContentResolver();


        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ed1.getText().toString();
                String passwprd = ed2.getText().toString();
                String age = ed3.getText().toString();
                if (name.isEmpty() ||passwprd.isEmpty() || age.isEmpty()) {
                    Toast.makeText(MainActivity.this, "数据不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                Uri uri = Uri.parse("content://com.example.administrator.chen.MyContentProvider/test");
                ContentValues values = new ContentValues();
                values.put("name", "陈玖旭");
                values.put("password", "123456");
                values.put("age", "23");
                Uri a = cr.insert(uri, values);
            }
        });
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


}
