package com.example.jszx.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
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
import android.view.Menu;
import android.view.MenuItem;
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
    private boolean isExit=false;
    private TextView page1;
    private TextView page2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        page1=(TextView)findViewById(R.id.is_not_finished);
        page2=(TextView)findViewById(R.id.is_finished);
        page1.setTextColor(Color.BLUE);
        page2.setTextColor(Color.BLACK);

        fragments=new ArrayList<>();
        fragments.add(new MainFragement());
        fragments.add(new SecondFragment());
        MyFragmentAdapter myFragmentAdapter=new MyFragmentAdapter(getSupportFragmentManager(),fragments);
        ViewPager viewPager=(ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(myFragmentAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                switch (position)
                {
                    case 0:
                        page1.setTextColor(Color.BLUE);
                        page2.setTextColor(Color.BLACK);
                        break;
                    case 1:
                        page1.setTextColor(Color.BLACK);
                        page2.setTextColor(Color.BLUE);
                        break;
                }
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


}
