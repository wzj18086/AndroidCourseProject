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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by 王志杰 on 2017/10/14.
 */

public class WeekDayAdapter extends RecyclerView.Adapter<WeekDayAdapter.ViewHolder> implements View.OnLongClickListener{
    private List<Plan> WeekDayPlanList;
    private MyonLongClickListener myonLongClickListener;
    Calendar calendar;
    static class ViewHolder extends RecyclerView.ViewHolder{
        Button weekDayTimeSelect;
        TextView weekDayPlanContent;
        public ViewHolder(View view)
        {
            super(view);
            weekDayTimeSelect=(Button)view.findViewById(R.id.weekday_timeSelect);
            weekDayPlanContent=(TextView)view.findViewById(R.id.weekday_planContext);
        }
    }
    public WeekDayAdapter(List<Plan> planList)
    {
        this.WeekDayPlanList=planList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.week_day_item_plan,parent,false);
        final ViewHolder viewHolder=new ViewHolder(view);

        //子项点击事件的响应
        viewHolder.weekDayTimeSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar= Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                int hour=calendar.get(Calendar.HOUR_OF_DAY);
                int day=calendar.get(Calendar.MINUTE);

                //计划只需要选择时刻选择框
                TimePickerDialog timePickerDialog=new TimePickerDialog(view.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        calendar.set(Calendar.HOUR,hourOfDay);
                        calendar.set(Calendar.MINUTE,minute);
                        viewHolder.weekDayTimeSelect.setText(String_to_time.Translate(hourOfDay)+":"+String_to_time.Translate(minute));
                        //mplanList.get(viewHolder.getAdapterPosition()).setDaedlineTime(new Date());
                    }
                },hour,day,true);
                timePickerDialog.show();
            }
        });
        viewHolder.weekDayPlanContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view=LayoutInflater.from(v.getContext()).inflate(R.layout.edit_dialog,null);
                final EditText editDialog=(EditText)view.findViewById(R.id.edit_dialog);
                editDialog.setText(WeekDayPlanList.get(viewHolder.getAdapterPosition()).getPlanContext());

                //编辑计划内容对话框的设置
                editDialog.setSelection(editDialog.getText().length());
                AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
                builder.setTitle("Type your plan")
                        .setView(view)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String DialogText=editDialog.getText().toString();
                                viewHolder.weekDayPlanContent.setText(DialogText);
                                WeekDayPlanList.get(viewHolder.getAdapterPosition()).setPlanContext(DialogText);
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
        Plan plan=WeekDayPlanList.get(position);
        holder.weekDayTimeSelect.setText(plan.getDaedlineTime());
        holder.weekDayPlanContent.setText(plan.getPlanContext());
    }

    @Override
    public int getItemCount() {
        return WeekDayPlanList.size();
    }
    public void add(Plan plan,int position)
    {
        if (WeekDayPlanList==null)
        {
            WeekDayPlanList=new ArrayList<>();
        }
        WeekDayPlanList.add(plan);
        notifyItemInserted(position);
    }
    public void removeItem(int pos) {
        WeekDayPlanList.remove(pos);
        notifyItemRemoved(pos);
    }

    @Override
    public boolean onLongClick(View view) {
        if(myonLongClickListener!=null)
        {
            myonLongClickListener.onItemLongClickListener(view,view.getId());
        }
        return true;
    }

    public static interface MyonLongClickListener
    {
        public void onItemLongClickListener(View view,int position);
    }
    public void setMyonLongClickListener(MyonLongClickListener listener)
    {
        this.myonLongClickListener=listener;
    }
    }
