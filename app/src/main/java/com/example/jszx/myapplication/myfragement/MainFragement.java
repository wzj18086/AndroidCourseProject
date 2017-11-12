package com.example.jszx.myapplication.myfragement;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jszx.myapplication.AlarmReciever;
import com.example.jszx.myapplication.MainActivity;
import com.example.jszx.myapplication.MainAdapter;
import com.example.jszx.myapplication.Plan;
import com.example.jszx.myapplication.R;

import org.litepal.crud.DataSupport;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by 王志杰 on 2017/11/7.
 */

public class MainFragement extends Fragment {
    private List<Plan> mainPlanList;
    private MainAdapter mainAdapter;
    private DrawerLayout drawerLayout;
    private Calendar calendar;
    private boolean isExit = false;
    private boolean isFirst;
    private List<Plan> eventList;
    private String TAG = "MainActivity";
    private String month;
    private String day_of_month;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment,null);

        swipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh);
        recyclerView = (RecyclerView) view.findViewById(R.id.main_recyclerview);
        calendar=Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));

        String day_of_week=String.valueOf(calendar.get(Calendar.DAY_OF_WEEK));

        day_of_month=String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        month=String.valueOf(calendar.get(Calendar.MONTH)+1);
        isFirst=false;

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Calendar calendar=Calendar.getInstance();
                String day_of_week=String.valueOf(calendar.get(Calendar.DAY_OF_WEEK));
                refreshList(day_of_week);
            }
        });

        //mainPlanList= DataSupport.where("weekday=? and isFinished=?",day_of_week,"false").find(Plan.class);
        mainPlanList= DataSupport.where("weekday=? and isFinished=?",day_of_week,"0").find(Plan.class);
        for(int i=0;i<mainPlanList.size();i++)
        {
            Log.d(TAG,mainPlanList.get(i).isFinished());
        }


        if(mainPlanList.size()!=0)
        {
            mainPlanList.get(0).setPosition(1);
        }

        mainAdapter=new MainAdapter(mainPlanList);
        final LinearLayoutManager layoutManager=new LinearLayoutManager(this.getActivity());

        recyclerView.setAdapter(mainAdapter);
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setAutoMeasureEnabled(true);
        int i1=mainAdapter.getItemCount();
        eventList=DataSupport.where("planType=? and isFinished=?","1","0").find(Plan.class);

        mainPlanList=addEvent(eventList,mainPlanList);
        mainAdapter.notifyDataSetChanged();
        setAlarm(mainPlanList);

        new ItemTouchHelper(new ItemTouchHelper.Callback() {
            private RecyclerView.ViewHolder vh;

            @Override
            public boolean isItemViewSwipeEnabled() {
                return true;
            }

            @Override
            public boolean isLongPressDragEnabled() {
                return true;
            }

            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder
                    viewHolder) {
                // 拖拽的标记，这里允许上下左右四个方向
                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT |
                        ItemTouchHelper.RIGHT;
                // 滑动的标记，这里允许左右滑动
                int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            /*
                这个方法会在某个Item被拖动和移动的时候回调，这里我们用来播放动画，当viewHolder不为空时为选中状态
                否则为释放状态
             */

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                // 移动时更改列表中对应的位置并返回true
                Collections.swap(mainPlanList, viewHolder.getAdapterPosition(), target
                        .getAdapterPosition());
                return true;
            }

            /*
                当onMove返回true时调用
             */
            @Override
            public void onMoved(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int
                    fromPos, RecyclerView.ViewHolder target, int toPos, int x, int y) {
                super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y);
                // 移动完成后刷新列表
                mainAdapter.notifyItemMoved(viewHolder.getAdapterPosition(), target
                        .getAdapterPosition());
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // 将数据集中的数据移除
                mainPlanList.get(viewHolder.getAdapterPosition()).setFinished("1");
                mainPlanList.get(viewHolder.getAdapterPosition()).save();
                Log.d(TAG,mainPlanList.get(viewHolder.getAdapterPosition()).isFinished());
                mainPlanList.remove(viewHolder.getAdapterPosition());

                // 刷新列表
                mainAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(recyclerView);
        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    private void setAlarm(List<Plan> mainPlanList)
    {
        for(int i=0;i<mainPlanList.size();i++)
        {
            String time=mainPlanList.get(i).getDaedlineTime();
            int hour;
            int minute;
            Calendar calendar1=Calendar.getInstance();
            if(mainPlanList.get(i).getPlanType().equals("0")) {
                hour = Integer.parseInt(time.split(":")[0]);
                minute = Integer.parseInt(time.split(":")[1]);
            }else {
                hour=Integer.parseInt(time.split("-")[2].split(" ")[1].split(":")[0]);
                minute=Integer.parseInt(time.split("-")[2].split(" ")[1].split(":")[1]);
            }
            calendar1.set(Calendar.HOUR_OF_DAY,hour);
            calendar1.set(Calendar.MINUTE,minute);
            calendar1.set(Calendar.SECOND,0);
            calendar1.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
            if(calendar1.after(calendar)) {
                Intent intent = new Intent(this.getActivity(), AlarmReciever.class);
                intent.putExtra("item",i);
                intent.putExtra("Type",mainPlanList.get(i).getPlanType());
                intent.putExtra("content", mainPlanList.get(i).getPlanContext());
                intent.setAction("ALARM_ACTION" + calendar1.getTimeInMillis());
                PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getActivity(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(), pendingIntent);
            }
        }
    }
    private List<Plan> addEvent(List<Plan> eventList, List<Plan> mainPlanList)
    {
        for (int temp=0;temp<eventList.size();temp++)
        {
            Plan event=eventList.get(temp);
            if (event.getDaedlineTime().split("-")[1].equals(month) && event.getDaedlineTime().split("-")[2].split(" ")[0].equals(day_of_month))
            {
                if(!isFirst)
                {
                    event.setPosition(1);
                    isFirst=true;
                }
                mainPlanList.add(event);
                //mainAdapter.notifyItemInserted(i1);
                //i1++;
            }

        }
        return mainPlanList;
    }
    public void refreshList(final String day_of_week)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                }catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        boolean isFirst=false;
                        Calendar calendar2=Calendar.getInstance();
                        day_of_month=String.valueOf(calendar2.get(Calendar.DAY_OF_MONTH));
                        month=String.valueOf(calendar2.get(Calendar.MONTH)+1);
                        List<Plan> mainPlanList_refrsh= DataSupport.where("weekday=?",day_of_week).find(Plan.class);
                        if(mainPlanList_refrsh.size()!=0)
                        {
                            mainPlanList_refrsh.get(0).setPosition(1);
                        }
                        mainPlanList_refrsh=addEvent(eventList,mainPlanList_refrsh);
                        mainPlanList.clear();
                        mainPlanList.addAll(mainPlanList_refrsh);
                        Log.d(TAG,mainPlanList.get(0).isFinished());
                        mainAdapter.notifyDataSetChanged();
                        setAlarm(mainPlanList);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }
}
