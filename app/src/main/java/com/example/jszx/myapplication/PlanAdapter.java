package com.example.jszx.myapplication;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by jszx on 2017/10/13.
 */

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.ViewHolder> {
    private List<Plan> mplanList;
    private Calendar calendar;

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        Button timeSelect;
        TextView planContext;
        public ViewHolder(View view)
        {
            super(view);
            timeSelect=(Button) view.findViewById(R.id.timeSelect);
            planContext=(TextView)view.findViewById(R.id.planContext);
        }
    }

    public PlanAdapter(List<Plan> planList)
    {
        mplanList=planList;
    }
    @Override
    public PlanAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.plan_item,parent,false);
        final ViewHolder viewHolder=new ViewHolder(view);

        //设置子项点击事件的响应
        viewHolder.timeSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar=Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                int hour=calendar.get(Calendar.HOUR_OF_DAY);
                int day=calendar.get(Calendar.MINUTE);
                //计划只需要选择时刻选择框
                TimePickerDialog timePickerDialog=new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        calendar.set(Calendar.HOUR,hourOfDay);
                        calendar.set(Calendar.MINUTE,minute);
                        viewHolder.timeSelect.setText(String_to_time.Translate(hourOfDay)+":"+String_to_time.Translate(minute));
                        //mplanList.get(viewHolder.getAdapterPosition()).setDaedlineTime(new Date());
                    }
                },hour,day,true);
                timePickerDialog.show();
            }
        });

        viewHolder.planContext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view=LayoutInflater.from(v.getContext()).inflate(R.layout.edit_dialog,null);
                final EditText editDialog=(EditText)view.findViewById(R.id.edit_dialog);
                editDialog.setText(mplanList.get(viewHolder.getAdapterPosition()).getPlanContext());
                editDialog.setSelection(editDialog.getText().length());

                //计划内容的编辑框的设置
                AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
                builder.setTitle("Type your plan")
                        .setView(view)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String DialogText=editDialog.getText().toString();
                                viewHolder.planContext.setText(DialogText);
                                mplanList.get(viewHolder.getAdapterPosition()).setPlanContext(DialogText);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                builder.create().show();
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Plan plan=mplanList.get(position);
        //holder.timeSelect.setText(plan.getPlanContext());
        holder.planContext.setText(plan.getPlanContext());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mplanList.size();
    }
    public void add(Plan plan,int position)
    {
        if (mplanList==null)
        {
            mplanList=new ArrayList<>();
        }
        mplanList.add(plan);
        notifyItemInserted(position);
    }

    public void removeItem(int pos){
        mplanList.remove(pos);
        notifyItemRemoved(pos);
    }
    }
