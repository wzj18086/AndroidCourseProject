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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EventActivity extends BaseActivity {
    List<Plan> events=new ArrayList<>();
    EventAdapter eventAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        events=DataSupport.where("planType=?","1").find(Plan.class);
        eventAdapter=new EventAdapter(events);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar_event);
        setSupportActionBar(toolbar);

        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.eventRecyclerView);
        final LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setAdapter(eventAdapter);
        recyclerView.setLayoutManager(layoutManager);
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.addNewEvent);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventAdapter.add(new Plan(), eventAdapter.getItemCount());
            }
        });
        Button ensure_button=(Button) findViewById(R.id.ensure_event);

        ensure_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataSupport.deleteAll(Plan.class,"planType=?","1");
                for (int i = 0; i <eventAdapter.getItemCount(); i++) {
                    View view1 = layoutManager.findViewByPosition(i);
                    CardView layout = (CardView) view1;
                    TextView textView = (TextView) layout.findViewById(R.id.eventContext);
                    Button button = (Button) layout.findViewById(R.id.event_timeSelect);
                    final String buttton_context = button.getText().toString();
                    final String textView_context = textView.getText().toString();
                    Plan event=new Plan();
                    event.setPlanContext(textView_context);
                    event.setDaedlineTime(buttton_context);
                    event.setPlanType("1");
                    event.save();
                }
                Intent intent=new Intent(EventActivity.this,MainActivity.class);
                startActivity(intent);
            }

        });
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
                Collections.swap(events, viewHolder.getAdapterPosition(), target
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
                eventAdapter.notifyItemMoved(viewHolder.getAdapterPosition(), target
                        .getAdapterPosition());
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // 将数据集中的数据移除
                events.remove(viewHolder.getAdapterPosition());
                // 刷新列表
                eventAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(recyclerView);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                this.finish();
                break;
        }
        return true;
    }
}
