package com.example.jszx.myapplication;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.litepal.crud.DataSupport;

import java.util.Collections;
import java.util.List;

public class WeekDayActivity extends BaseActivity {
    private List<Plan> weekdayPlanList;
    private WeekDayAdapter weekDayAdapter;
    private LinearLayoutManager layoutManager;
    private String day_of_week;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.week_day_plan);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent=getIntent();
        day_of_week=intent.getStringExtra("day_of_week");
        weekdayPlanList= DataSupport.where("weekday=?",day_of_week).find(Plan.class);
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.planRecyclerView_weekday);
        weekDayAdapter=new WeekDayAdapter(weekdayPlanList);
        recyclerView.setAdapter(weekDayAdapter);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

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
                Collections.swap(weekdayPlanList, viewHolder.getAdapterPosition(), target
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
                weekDayAdapter.notifyItemMoved(viewHolder.getAdapterPosition(), target
                        .getAdapterPosition());
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // 将数据集中的数据移除
                weekdayPlanList.remove(viewHolder.getAdapterPosition());
                // 刷新列表
                weekDayAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(recyclerView);

        com.github.clans.fab.FloatingActionButton floatingActionButton = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.addNewPlan_weekday);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               weekDayAdapter.add(new Plan(), weekDayAdapter.getItemCount());
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                this.finish();
                break;
            case R.id.save_data:
                DataSupport.deleteAll(Plan.class,"weekday=?",day_of_week);
                for (int i = 0; i < weekDayAdapter.getItemCount(); i++) {
                    View view1 = layoutManager.findViewByPosition(i);
                    CardView layout = (CardView) view1;
                    TextView textView = (TextView) layout.findViewById(R.id.weekday_planContext);
                    Button button = (Button) layout.findViewById(R.id.weekday_timeSelect);
                    final String buttton_context = button.getText().toString();
                    final String textView_context = textView.getText().toString();
                    Plan plan=new Plan();
                    plan.setWeekday(day_of_week);
                    plan.setPlanContext(textView_context);
                    plan.setDaedlineTime(buttton_context);
                    plan.setPlanType("0");
                    if(plan.isFinished()==null)
                    {
                        plan.setFinished("0");
                        plan.save();
                    }else
                    {
                        plan.save();
                    }

                }
                Intent intent=new Intent(WeekDayActivity.this,MainActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }
}
