package com.realmdatabaseexample.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by esec-sruthi on 21/10/17.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewholder> {

    private List list;
    private Activity activity;
    private int layout;
    private ReturnView returnView;
    private int from;

    public RecyclerAdapter(List list,Activity activity,ReturnView returnView,int layout,int from) {
        this.list=list;
        this.activity=activity;
        this.returnView=returnView;
        this.layout=layout;
        this.from=from;
    }

    @Override
    public MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(activity).inflate(layout,parent,false);
        return new MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(MyViewholder holder, int position) {
        returnView.getactivityView(holder.view,list,position,from);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewholder extends RecyclerView.ViewHolder {
        View view;
        public MyViewholder(View itemView) {
            super(itemView);
            this.view=itemView;
        }
    }

    public interface ReturnView{
        public void getactivityView(View view,List list,int pos,int from);
    }
}
