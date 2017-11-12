package com.example.jszx.myapplication.desktopwidget;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.jszx.myapplication.Plan;
import com.example.jszx.myapplication.R;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by 王志杰 on 2017/11/3.
 */

public class ListViewService extends RemoteViewsService {


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this.getApplicationContext(),intent);
    }

    public class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory
    {
        private Context mcontext;
        private List<Plan> planList=new ArrayList<>();
        public ListRemoteViewsFactory(Context context,Intent intent)
        {
            mcontext=context;
        }
        @Override
        public void onCreate() {
            Calendar calendar=Calendar.getInstance();
            String day_of_week=String.valueOf(calendar.get(Calendar.DAY_OF_WEEK));
            planList=DataSupport.where("weekday=?",day_of_week).find(Plan.class);
        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return planList.size();
        }

        @Override
        public RemoteViews getViewAt(int i) {
            RemoteViews remoteViews=new RemoteViews(mcontext.getPackageName(), android.R.layout.simple_list_item_1);
            remoteViews.setTextViewText(android.R.id.text1,planList.get(i).getDaedlineTime()+" "+planList.get(i).getPlanContext());
            return remoteViews;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}
