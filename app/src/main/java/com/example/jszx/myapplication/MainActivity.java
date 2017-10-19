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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.litepal.crud.DataSupport;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
    private List<Plan> mainPlanList;
    private MainAdapter mainAdapter;
    private DrawerLayout drawerLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        final String day_of_week=String.valueOf(calendar.get(Calendar.DAY_OF_WEEK));

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshList(day_of_week);
            }
        });


        Log.d("MainActivity",day_of_week);
        mainPlanList= DataSupport.where("weekday=?",day_of_week).find(Plan.class);
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.main_recyclerview);
        mainAdapter=new MainAdapter(mainPlanList);
        final LinearLayoutManager layoutManager=new LinearLayoutManager(this);

        recyclerView.setAdapter(mainAdapter);
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setAutoMeasureEnabled(true);
        for(int i=0;i<mainPlanList.size();i++)
        {
            String time=mainPlanList.get(i).getDaedlineTime();
            Log.d("MainActivity",time);

            int hour=Integer.parseInt(time.split(":")[0]);
            int minute=Integer.parseInt(time.split(":")[1]);
            Calendar calendar1=Calendar.getInstance();
            calendar1.set(Calendar.HOUR_OF_DAY,hour);
            calendar1.set(Calendar.MINUTE,minute);
            calendar1.set(Calendar.SECOND,0);
            calendar1.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
            if(calendar1.after(calendar)) {
                Intent intent = new Intent(this, AlarmReciever.class);
                intent.putExtra("day_of_week", day_of_week);
                intent.putExtra("plan_item", i);
                intent.setAction("ALARM_ACTION" + calendar1.getTimeInMillis());
                PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(), pendingIntent);
            }

        }

        drawerLayout=(DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_launcher);
        }

        NavigationView navigationView=(NavigationView)findViewById(R.id.navigation_view);
        //navigationView.setCheckedItem(R.id.main_inferance);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.addPlan:
                Intent intent=new Intent(this,planActivity.class);
                startActivity(intent);
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
                        mainPlanList= DataSupport.where("weekday=?",day_of_week).find(Plan.class);
                        mainAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }
}
