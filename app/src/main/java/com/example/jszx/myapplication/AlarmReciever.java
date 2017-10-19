package com.example.jszx.myapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.List;

public class AlarmReciever extends BroadcastReceiver {
    private List<Plan> mplanList;
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        String day_of_week=intent.getStringExtra("day_of_week");
        int plan_item=intent.getIntExtra("plan_item",4);
        mplanList= DataSupport.where("weekday=?",day_of_week).find(Plan.class);
        Toast.makeText(context,mplanList.get(plan_item).getDaedlineTime()+" "+mplanList.get(plan_item).getPlanContext(),Toast.LENGTH_SHORT).show();
        NotificationManager manager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification=new Notification.Builder(context)
                .setContentTitle("时间到了！！！！！")
                .setContentText(mplanList.get(plan_item).getDaedlineTime()+" "+mplanList.get(plan_item).getPlanContext())
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_launcher))
                .build();
        manager.notify(mplanList.get(plan_item).getId(),notification);
    }
}
