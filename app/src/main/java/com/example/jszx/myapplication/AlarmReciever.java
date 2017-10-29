package com.example.jszx.myapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.Calendar;
import java.util.List;

public class AlarmReciever extends BroadcastReceiver {
    private List<Plan> mplanList;
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
/*
        String day_of_week=intent.getStringExtra("day_of_week");

        mplanList= DataSupport.where("weekday=?",day_of_week).find(Plan.class);
        Toast.makeText(context,mplanList.get(plan_item).getDaedlineTime()+" "+mplanList.get(plan_item).getPlanContext(),Toast.LENGTH_SHORT).show();*/

        String content=intent.getStringExtra("content");
        int plan_item=intent.getIntExtra("item",0);
        String type=intent.getStringExtra("Type");
        Calendar calendar=Calendar.getInstance();
        String hour=String.valueOf(calendar.get(Calendar.HOUR));
        String minute=String.valueOf(calendar.get(Calendar.MINUTE));

        Toast.makeText(context,hour+" "+minute+content,Toast.LENGTH_SHORT).show();
        NotificationManager manager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        if(type.equals("0"))
        {
            Notification notification=new Notification.Builder(context)
                    .setContentTitle("计划时间到了！！！！")
                    .setContentText(hour+" "+minute+" "+content)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_launcher))
                    .build();
            manager.notify(plan_item,notification);
        }else
        {
            Notification notification=new Notification.Builder(context)
                    .setContentTitle("备忘时间到了！！！！")
                    .setContentText(hour+" "+minute+" "+content)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_launcher))
                    .build();
            manager.notify(plan_item,notification);
        }

    }
}
