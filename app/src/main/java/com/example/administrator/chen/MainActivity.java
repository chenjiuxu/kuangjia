package com.example.administrator.chen;


import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.LoaderManager;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.io.IOException;


public class MainActivity extends ActionBarActivity {
    private Toolbar toolbar;
    private EditText ed1;
    private EditText ed2;
    private EditText ed3;
    private ContentResolver cr;
    private ListView listview;
    public Uri uri;
    private SimpleCursorAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        uri = Uri.parse("content://com.example.administrator.chen.MyContentProvider/test");
        ed1 = (EditText) findViewById(R.id.editText1);
        ed2 = (EditText) findViewById(R.id.editText2);
        ed3 = (EditText) findViewById(R.id.editTex3);
        listview = (ListView) findViewById(R.id.listView);
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

                getToken();
//
//                String name = ed1.getText().toString();
//                String passwprd = ed2.getText().toString();
//                String age = ed3.getText().toString();
//                if (name.isEmpty() || passwprd.isEmpty() || age.isEmpty()) {
//                    Toast.makeText(MainActivity.this, "数据不能为空", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                ContentValues values = new ContentValues();
//                values.put("name", name);
//                values.put("password", passwprd);
//                values.put("age", age);
//                Uri a = cr.insert(uri, values);
            }
        });
        LoaderManager lm = getSupportLoaderManager();
        Cursor test = cr.query(uri, null, null, null, null);
        Log.d("查到没有", test.getCount() + "");
        Toast.makeText(MainActivity.this, test.toString(), Toast.LENGTH_LONG).show();
        test.moveToFirst();
        while (test.moveToNext()) {
            int a = test.getColumnIndex("name");
            String name = test.getString(1);
            String password = test.getString(2);
            String age = test.getString(3);
            Log.e("name=", name);
            Log.e("password=", password);
            Log.e("age=", age);
        }
        adapter = new SimpleCursorAdapter(MainActivity.this, R.layout.item, test, new String[]{"name", "password", "age"}, new int[]{R.id.item1, R.id.item2, R.id.item3}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        listview.setAdapter(adapter);
        lm.initLoader(0, null, new LoaderManager.LoaderCallbacks<Cursor>() {
            @Override
            public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
                android.support.v4.content.CursorLoader loader = new android.support.v4.content.CursorLoader(MainActivity.this, uri, null, null, null, null);
                return loader;
            }

            @Override
            public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
                adapter.swapCursor(data);

            }

            @Override
            public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {
                adapter.swapCursor(null);
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

    private class Adapter extends SimpleCursorAdapter {


        public Adapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
            super(context, layout, c, from, to, flags);
        }
    }

    private void getToken() {
        AccountManager.get(this).getAuthTokenByFeatures(constant.accountType,constant.TokenType, new String[0], this, null, null,new  callback(), null);
    }
        private class callback implements AccountManagerCallback<Bundle> {
        @Override
        public void run(AccountManagerFuture<Bundle> future) {

        }
    }

}

