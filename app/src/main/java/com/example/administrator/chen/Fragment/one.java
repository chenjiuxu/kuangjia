package com.example.administrator.chen.Fragment;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.LoaderManager;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.chen.R;


public class one extends Fragment implements View.OnClickListener ,AdapterView.OnItemLongClickListener {


    private ContentResolver cr;
    private ActionBarActivity activity;
    private LoaderManager manager;
    private String age;
    private String password;
    private String name;
    private SimpleCursorAdapter adapter;
    private Uri uri;
    private EditText ed_name;
    private EditText ed_password;
    private ListView listview;
    private EditText ed_age;


    @Override
    public void onCreate(Bundle savedInstanceState) {//创建fragment时初始化东西，不初始化view
        super.onCreate(savedInstanceState);
        activity = (ActionBarActivity) getActivity();//获得关联的Activity
        cr = activity.getContentResolver();//获得操作数据库ContentResolver
        manager = activity.getSupportLoaderManager();//获得数据库同步对象


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {//初始化页面view
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        initial(view);
        return view;
    }

    private void initial(View view) {
        ed_name = (EditText) view.findViewById(R.id.one_ed1);
        ed_password = (EditText) view.findViewById(R.id.one_ed2);
        ed_age = (EditText) view.findViewById(R.id.one_ed3);
        listview = (ListView) view.findViewById(R.id.one_listview);
        View bt = view.findViewById(R.id.one_bt);
        bt.setOnClickListener(this);
        setAdapter(view, listview);
    }

    /**
     * 初始化页面Adapter
     */
    private void setAdapter(View view, ListView listview) {
        uri = Uri.parse("content://com.example.administrator.chen.MyContentProvider/test");//指向数据库的URI
        Cursor cursor = cr.query(uri, null, null, null, null);
        adapter = new SimpleCursorAdapter(view.getContext(), R.layout.item, cursor,
                new String[]{"_id", "name", "password", "age"}, new int[]{R.id.item1, R.id.item2, R.id.item3, R.id.item4},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        listview.setAdapter(adapter);
        listview.setOnItemLongClickListener(this);
     
        sqliteListener(view);

    }

    /**
     * 设置监听器监听数据库变化并改变界面
     */
    private void sqliteListener(final View view) {
        manager.initLoader(0, null, new LoaderManager.LoaderCallbacks<Cursor>() {
            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                android.support.v4.content.CursorLoader cursor =
                        new android.support.v4.content.CursorLoader(view.getContext(), uri, null, null, null, null);
                return cursor;
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor data) {//数据库发生变化时执行
                adapter.swapCursor(data);
            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {//
                adapter.swapCursor(null);

            }
        });

    }


    @Override
    public void onAttach(Activity activity) {//和activity关联时被调用
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {//和activity失去关联被调用
        super.onDetach();

    }

    @Override
    public void onClick(View v) {//确定写入数据库
        age = ed_age.getText().toString();
        password = ed_password.getText().toString();
        name = ed_name.getText().toString();
        if (age == null || password == null || name == null) {
            Toast.makeText(v.getContext(), "内容不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("password", password);
        values.put("age", age);
        Toast.makeText(v.getContext(), name + password + age, Toast.LENGTH_SHORT).show();
        cr.insert(uri, values);

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        TextView tv = (TextView) view.findViewById(R.id.item1);
        String a = tv.getText().toString();
        Toast.makeText(view.getContext(),a,Toast.LENGTH_SHORT).show();
        cr.delete(uri,"_id=?",new String[]{a});


        return false;
    }
}
