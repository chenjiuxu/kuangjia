package com.example.administrator.chen.Fragment;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;


import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.chen.R;

import java.util.ArrayList;

public class three extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {


    private Activity activity;
    private Base a;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();


        a = new Base();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_three, null);
        ListView listview = (ListView) view.findViewById(R.id.fragment_three_listview);
        listview.setAdapter(a);
        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private class Adapter extends CursorAdapter {

        public Adapter(Context context, Cursor c, boolean autoRequery) {
            super(context, c, autoRequery);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return null;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {

        }
    }


    private class Base extends BaseAdapter {


        String[] a = new String[]{"2015", "2015", "2015", "2015", "2016", "2016", "2016", "2017", "2017", "2017", "2018", "2018", "2018", "2018"};
        ArrayList<View> list = new ArrayList<View>();
        int b = 0;
        private View views = null;

        @Override
        public int getCount() {
            return a.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item1, null);
            }
            if (i % 2 != 0) {

                view.findViewById(R.id.iv).setVisibility(View.GONE);

            } else {
                view.findViewById(R.id.iv).setVisibility(View.VISIBLE);
            }
            TextView tv = (TextView) view.findViewById(R.id.test_tv);
            String s1 = a[i];
            if (i == 0 || a[i].equals(a[i - 1])) {
                tv.setText("");
                if (i == 0) {
                    view.findViewById(R.id.time_axle_time).setVisibility(View.VISIBLE);
                    tv.setText(s1 + "年");
                } else {
                    view.findViewById(R.id.time_axle_time).setVisibility(View.GONE);
                }

            } else {
                view.findViewById(R.id.time_axle_time).setVisibility(View.GONE);
                tv.setText(s1 + "年");
            }
            return view;
        }
    }
}
