package com.example.jszx.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by 王志杰 on 2017/10/14.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private List<Plan> mainPlanList;
    static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView time_select;
        TextView content;
        TextView isFinish;
        TextView headerView;
        View line;
        public ViewHolder(View view)
        {
            super(view);
            headerView=(TextView)view.findViewById(R.id.header_view);
            line=(View)view.findViewById(R.id.line);
            time_select=(TextView) view.findViewById(R.id.main_time);
            content=(TextView) view.findViewById(R.id.content);
            //isFinish=(TextView)view.findViewById(R.id.isFinish);
        }
    }
    public MainAdapter(List<Plan> planList)
    {
        this.mainPlanList=planList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Plan plan=mainPlanList.get(position);
        if(plan.getPosition()!=0 && plan.getPlanType().equals("0"))
        {
            holder.headerView.setVisibility(View.VISIBLE);
            holder.line.setVisibility(View.VISIBLE);
            holder.headerView.setText("计划");

        }else if (plan.getPosition()!=0 && plan.getPlanType().equals("1"))
        {
            holder.headerView.setVisibility(View.VISIBLE);
            holder.line.setVisibility(View.VISIBLE);
            holder.headerView.setText("备忘");
        }else
        {
            holder.headerView.setVisibility(View.GONE);
            holder.line.setVisibility(View.GONE);
        }

        if(plan.getPlanType().equals("0"))
        {
            holder.time_select.setText(plan.getDaedlineTime());
        }else
        {
            holder.time_select.setText(plan.getDaedlineTime().split("-")[2].split(" ")[1].split(":")[0]+":"+plan.getDaedlineTime().split("-")[2].split(" ")[1].split(":")[1]);
        }

        holder.content.setText(plan.getPlanContext());

    }

    @Override
    public int getItemCount() {
        return mainPlanList.size();
    }
}
