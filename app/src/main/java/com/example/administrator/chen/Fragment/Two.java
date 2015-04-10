package com.example.administrator.chen.Fragment;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.chen.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedHashMap;


public class Two extends Fragment {

    private FragmentActivity activity;

    private MyAdapter mAdapter;
    private ListView listview;
    private ArrayList<String> list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        activity = getActivity();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        listview = (ListView) view.findViewById(R.id.listView);
        setAdapter();
        return view;
    }

    private void setAdapter() {
        Animation animation = AnimationUtils.loadAnimation(activity, R.anim.slide_right);
        LayoutAnimationController lac = new LayoutAnimationController(animation);
        lac.setOrder(LayoutAnimationController.ORDER_NORMAL);
        listview.setLayoutAnimation(lac);
        list = new ArrayList<String>();
        for (int i = 0; i <= 100; i++) {
            list.add("陈玖旭");
        }
        listview.setAdapter(new MyAdapter(list));

    }

    private static class MyAdapter extends BaseAdapter {

        ArrayList<String> list;

        public MyAdapter(ArrayList<String> list1) {
            this.list = list1;
        }

        @Override
        public int getCount() {
            return list.size();
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
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_textview, null);
            } else {
                TextView tv = (TextView) view.findViewById(R.id.item_editText);
                tv.setText(list.get(i));
            }


            return view;
        }
    }

}
