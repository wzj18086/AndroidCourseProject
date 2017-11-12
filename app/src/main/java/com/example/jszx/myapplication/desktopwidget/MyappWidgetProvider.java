package com.example.jszx.myapplication.desktopwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.example.jszx.myapplication.R;

/**
 * Created by 王志杰 on 2017/11/3.
 */

public class MyappWidgetProvider extends AppWidgetProvider {
    public static final String REFRESH_ACTION = "com.example.jszx.myapplication.desktopwidget.action.CLICK";
    private RemoteViews remoteViews;
    public MyappWidgetProvider() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context,intent);

        if(REFRESH_ACTION.equals(intent.getAction()))
        {
            remoteViews=new RemoteViews(context.getPackageName(), R.layout.desktop_widget);
            AppWidgetManager appWidgetManager=AppWidgetManager.getInstance(context);
            Intent service_intent=new Intent(context,ListViewService.class);
            remoteViews.setRemoteAdapter(R.id.listview,service_intent);
            remoteViews.setEmptyView(R.id.listview,android.R.id.empty);
            ComponentName componentName=new ComponentName(context,MyappWidgetProvider.class);
            appWidgetManager.updateAppWidget(componentName,remoteViews);

        }

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        remoteViews=new RemoteViews(context.getPackageName(), R.layout.desktop_widget);
        Intent service_intent=new Intent(context,ListViewService.class);
        remoteViews.setRemoteAdapter(R.id.listview,service_intent);
        remoteViews.setEmptyView(R.id.listview,android.R.id.empty);
        Intent refresh_intent=new Intent(REFRESH_ACTION);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(context,R.id.refresh_button,refresh_intent,PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.refresh_button,pendingIntent);
        for(int appWidgetID:appWidgetIds)
        {
            appWidgetManager.updateAppWidget(appWidgetID,remoteViews);
        }

    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }

    @Override
    public void onRestored(Context context, int[] oldWidgetIds, int[] newWidgetIds) {
        super.onRestored(context, oldWidgetIds, newWidgetIds);
    }
}
