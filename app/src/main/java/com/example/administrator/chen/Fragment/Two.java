package com.example.administrator.chen.Fragment;


import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.chen.R;
import com.nhaarman.listviewanimations.appearance.SingleAnimationAdapter;
import com.nineoldandroids.animation.ObjectAnimator;

import java.util.ArrayList;


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
        AnimationAdapter animationAdapter = new AnimationAdapter(new MyAdapter(list));
        animationAdapter.setAbsListView(listview);
        listview.setAdapter(animationAdapter);
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
            if(i<=20){
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_textview, null);
            }
                TextView tv = (TextView) view.findViewById(R.id.item_editText);
                tv.setText(list.get(i));


            return view;
        }
    }

    private static class AnimationAdapter extends SingleAnimationAdapter {


        protected AnimationAdapter(@NonNull BaseAdapter baseAdapter) {
            super(baseAdapter);
        }

        @NonNull
        @Override
        protected com.nineoldandroids.animation.Animator getAnimator(@NonNull ViewGroup viewGroup, @NonNull View view) {
            ObjectAnimator Animator = ObjectAnimator.ofFloat(view, "translationY", 500, 0);
            return Animator;
        }
    }

}
