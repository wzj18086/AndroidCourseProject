package com.example.jszx.myapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;


import java.util.Calendar;
import java.util.List;

public class AlarmReciever extends BroadcastReceiver {
    private List<Plan> mplanList;
    @Override
    public void onReceive(Context context, Intent intent) {

        String content=intent.getStringExtra("content");
        int plan_item=intent.getIntExtra("item",0);
        String type=intent.getStringExtra("Type");
        Calendar calendar=Calendar.getInstance();
        String hour=String.valueOf(calendar.get(Calendar.HOUR));
        String minute=String.valueOf(calendar.get(Calendar.MINUTE));

        Toast.makeText(context,hour+":"+minute+content,Toast.LENGTH_SHORT).show();
        NotificationManager manager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent1=new Intent(context,MainActivity.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,0,intent1,PendingIntent.FLAG_CANCEL_CURRENT);
        if(type.equals("0"))
        {
            Notification notification=new Notification.Builder(context)
                    .setContentTitle("计划时间到了！")
                    .setContentText(hour+":"+minute+" "+content)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_launcher))
                    .setSmallIcon(R.mipmap.icon)
                    .build();
            manager.notify(plan_item,notification);
        }else
        {
            Notification notification=new Notification.Builder(context)
                    .setContentTitle("备忘时间到了！")
                    .setContentText(hour+" "+minute+" "+content)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_launcher))
                    .setPriority(Notification.PRIORITY_MAX)
                    .setContentIntent(pendingIntent)
                    .build();
            manager.notify(plan_item,notification);
        }

    }
}
