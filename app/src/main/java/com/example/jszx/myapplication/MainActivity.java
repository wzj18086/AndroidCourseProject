package com.example.jszx.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jszx.myapplication.myfragement.MainFragement;
import com.example.jszx.myapplication.myfragement.MyFragmentAdapter;
import com.example.jszx.myapplication.myfragement.SecondFragment;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class MainActivity extends BaseActivity {
    private DrawerLayout drawerLayout;
    private List<Fragment> fragments;
    private List<String> tabName;
    private boolean isExit=false;
    boolean isSpinnerFirst = true ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragments=new ArrayList<>();
        fragments.add(new MainFragement());
        fragments.add(new SecondFragment());
        tabName=new ArrayList<>();
        tabName.add("未完成");
        tabName.add("已完成");
        TabLayout tabLayout=(TabLayout)findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(tabName.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(tabName.get(1)));
        MyFragmentAdapter myFragmentAdapter=new MyFragmentAdapter(getSupportFragmentManager(),fragments,tabName);
        ViewPager viewPager=(ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(myFragmentAdapter);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout=(DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.navigation_menu);
        }

        NavigationView navigationView=(NavigationView)findViewById(R.id.navigation_view);
        //View view= navigationView.inflateHeaderView(R.layout.menu_header);
        View view=navigationView.getHeaderView(0);
        TextView menuHeader=(TextView)view.findViewById(R.id.menu_header);
        Calendar calendar=Calendar.getInstance();
        menuHeader.setText(String.valueOf(calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH)));
        TextView weekday=(TextView)findViewById(R.id.weekday);
        switch (calendar.get(Calendar.DAY_OF_WEEK))
        {
            case 1:
                weekday.setText("Sunday");
                break;
            case 2:
                weekday.setText("Monday");
                break;
            case 3:
                weekday.setText("Tuesday");
                break;
            case 4:
                weekday.setText("Wednesday");
                break;
            case 5:
                weekday.setText("Thursday");
                break;
            case 6:
                weekday.setText("Friday");
                break;
            case 7:
                weekday.setText("Saturday");
                break;
        }
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
                    case R.id.weather:
                        Intent intent_weather=new Intent(MainActivity.this,com.example.jszx.myapplication.coolweather.MainActivity.class);
                        startActivity(intent_weather);
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
                    case R.id.exit:
                        ActivityController.finishAll();
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


}
