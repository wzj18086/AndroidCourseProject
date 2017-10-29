package com.example.jszx.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import org.litepal.crud.DataSupport;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class MainActivity extends BaseActivity {
    private List<Plan> mainPlanList;
    private MainAdapter mainAdapter;
    private DrawerLayout drawerLayout;
    private Calendar calendar;
    private boolean isExit=false;
    private boolean isFirst;
    private List<Plan> eventList;
    private String TAG="MainActivity";
    private String month;
    private String day_of_month;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        calendar=Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));

        String day_of_week=String.valueOf(calendar.get(Calendar.DAY_OF_WEEK));

        day_of_month=String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        month=String.valueOf(calendar.get(Calendar.MONTH)+1);
        isFirst=false;

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Calendar calendar=Calendar.getInstance();
                String day_of_week=String.valueOf(calendar.get(Calendar.DAY_OF_WEEK));
                refreshList(day_of_week);
            }
        });


        mainPlanList= DataSupport.where("weekday=? ",day_of_week).find(Plan.class);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.main_recyclerview);
        if(mainPlanList.size()!=0)
        {
            mainPlanList.get(0).setPosition(1);
        }

        mainAdapter=new MainAdapter(mainPlanList);
        final LinearLayoutManager layoutManager=new LinearLayoutManager(this);

        recyclerView.setAdapter(mainAdapter);
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setAutoMeasureEnabled(true);
        int i1=mainAdapter.getItemCount();
        eventList=DataSupport.where("planType=?","1").find(Plan.class);

        mainPlanList=addEvent(eventList,mainPlanList);
        mainAdapter.notifyDataSetChanged();
        setAlarm(mainPlanList);


        drawerLayout=(DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_launcher);
        }

        NavigationView navigationView=(NavigationView)findViewById(R.id.navigation_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case android.R.id.home:
                        drawerLayout.openDrawer(GravityCompat.START);
                        break;
                    case R.id.addPlan:
                        Intent intent=new Intent(MainActivity.this,planActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.addEvent:
                        Intent intent_event=new Intent(MainActivity.this,EventActivity.class);
                        startActivity(intent_event);
                        break;
                    case R.id.Mon:
                        Intent intent2=new Intent(MainActivity.this,WeekDayActivity.class);
                        intent2.putExtra("day_of_week","2");
                        startActivity(intent2);
                        break;
                    case R.id.Tue:
                        Intent intent3=new Intent(MainActivity.this,WeekDayActivity.class);
                        intent3.putExtra("day_of_week","3");
                        startActivity(intent3);
                        break;
                    case R.id.Wed:
                        Intent intent4=new Intent(MainActivity.this,WeekDayActivity.class);
                        intent4.putExtra("day_of_week","4");
                        startActivity(intent4);
                        break;
                    case R.id.Thu:
                        Intent intent5=new Intent(MainActivity.this,WeekDayActivity.class);
                        intent5.putExtra("day_of_week","5");
                        startActivity(intent5);
                        break;
                    case R.id.Fri:
                        Intent intent6=new Intent(MainActivity.this,WeekDayActivity.class);
                        intent6.putExtra("day_of_week","6");
                        startActivity(intent6);
                        break;
                    case R.id.Sat:
                        Intent intent7=new Intent(MainActivity.this,WeekDayActivity.class);
                        intent7.putExtra("day_of_week","7");
                        startActivity(intent7);
                        break;
                    case R.id.Sun:
                        Intent intent1=new Intent(MainActivity.this,WeekDayActivity.class);
                        intent1.putExtra("day_of_week","1");
                        startActivity(intent1);
                        break;

                }
                return true;
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            if(!isExit) {
                Toast.makeText(this, "再按一次返回键退出", Toast.LENGTH_SHORT).show();
                isExit=true;
            }else {
                ActivityController.finishAll();
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
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
                runOnUiThread(new Runnable() {
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
                        mainAdapter.notifyDataSetChanged();
                        setAlarm(mainPlanList);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
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
                Intent intent = new Intent(this, AlarmReciever.class);
                intent.putExtra("item",i);
                intent.putExtra("Type",mainPlanList.get(i).getPlanType());
                intent.putExtra("content", mainPlanList.get(i).getPlanContext());
                intent.setAction("ALARM_ACTION" + calendar1.getTimeInMillis());
                PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
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
}
