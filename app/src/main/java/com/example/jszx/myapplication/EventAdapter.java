package com.example.jszx.myapplication;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by 王志杰 on 2017/10/25.
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> implements View.OnLongClickListener{
    private List<Plan> EventList;
    private EventAdapter.MyonLongClickListener myonLongClickListener;
    Calendar calendar;
    static class ViewHolder extends RecyclerView.ViewHolder{
        Button eventTimeSelect;
        TextView eventContent;
        public ViewHolder(View view)
        {
            super(view);
            eventTimeSelect=(Button)view.findViewById(R.id.event_timeSelect);
            eventContent=(TextView)view.findViewById(R.id.eventContext);
        }
    }
    public EventAdapter(List<Plan> eventList)
    {
        this.EventList=eventList;
    }

    @Override
    public EventAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item,parent,false);
        final EventAdapter.ViewHolder viewHolder=new EventAdapter.ViewHolder(view);
        viewHolder.eventTimeSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar= Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                final int year=calendar.get(Calendar.YEAR);
                int month=calendar.get(Calendar.MONTH);
                int day=calendar.get(Calendar.DAY_OF_MONTH);
                int hour=calendar.get(Calendar.HOUR_OF_DAY);
                int minute=calendar.get(Calendar.MINUTE);
                DatePickerDialog datePickerDialog=new DatePickerDialog(view.getContext(),0,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendar.set(Calendar.YEAR,i);
                        calendar.set(Calendar.MONTH,i1);
                        calendar.set(Calendar.DAY_OF_MONTH,i2);
                    }
                },year,month,day);

                TimePickerDialog timePickerDialog=new TimePickerDialog(view.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        calendar.set(Calendar.HOUR,hourOfDay);
                        calendar.set(Calendar.MINUTE,minute);
                        viewHolder.eventTimeSelect.setText(calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+(calendar.get(Calendar.DAY_OF_MONTH)-1)+" "+String_to_time.Translate(hourOfDay)+":"+String_to_time.Translate(minute));
                        //mplanList.get(viewHolder.getAdapterPosition()).setDaedlineTime(new Date());
                    }
                },hour,minute,true);
                timePickerDialog.show();
                datePickerDialog.show();

            }
        });
        viewHolder.eventContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view=LayoutInflater.from(v.getContext()).inflate(R.layout.edit_dialog,null);
                final EditText editDialog=(EditText)view.findViewById(R.id.edit_dialog);
                editDialog.setText(EventList.get(viewHolder.getAdapterPosition()).getPlanContext());
                editDialog.setSelection(editDialog.getText().length());
                AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
                builder.setTitle("Type your plan")
                        .setView(view)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String DialogText=editDialog.getText().toString();
                                viewHolder.eventContent.setText(DialogText);
                                EventList.get(viewHolder.getAdapterPosition()).setPlanContext(DialogText);
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
    public void onBindViewHolder(EventAdapter.ViewHolder holder, int position) {
        Plan event=EventList.get(position);
        holder.eventTimeSelect.setText(event.getDaedlineTime());
        holder.eventContent.setText(event.getPlanContext());
    }

    @Override
    public int getItemCount() {
        return EventList.size();
    }
    public void add(Plan event,int position)
    {
        if (EventList==null)
        {
            EventList=new ArrayList<>();
        }
        EventList.add(event);
        notifyItemInserted(position);
    }
    public void removeItem(int pos) {
        EventList.remove(pos);
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
    public void setMyonLongClickListener(EventAdapter.MyonLongClickListener listener)
    {
        this.myonLongClickListener=listener;
    }
}
