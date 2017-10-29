package com.example.jszx.myapplication;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class planActivity extends BaseActivity {
    private List<Plan> mplanList = new ArrayList<>();
    private PlanAdapter planAdapter;
    private CheckBox Monday;
    private CheckBox Tuesday;
    private CheckBox Wednesday;
    private CheckBox Thursday;
    private CheckBox Friday;
    private CheckBox Saturday;
    private CheckBox Sunday;
    private Button ensureData;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plan);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.planRecyclerView);
        planAdapter = new PlanAdapter(mplanList);
        recyclerView.setAdapter(planAdapter);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(layoutManager);



        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.addNewPlan);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                planAdapter.add(new Plan(), planAdapter.getItemCount());
            }
        });
        Monday = (CheckBox) findViewById(R.id.Monday);
        Tuesday = (CheckBox) findViewById(R.id.Tuesday);
        Wednesday = (CheckBox) findViewById(R.id.Wednsday);
        Thursday = (CheckBox) findViewById(R.id.Thusday);
        Friday = (CheckBox) findViewById(R.id.Friday);
        Saturday = (CheckBox) findViewById(R.id.Saturday);
        Sunday = (CheckBox) findViewById(R.id.Sunday);
        ensureData = (Button) findViewById(R.id.ensure);

        ensureData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < planAdapter.getItemCount(); i++) {
                    View view1 = layoutManager.findViewByPosition(i);
                    CardView layout = (CardView) view1;
                    TextView textView = (TextView) layout.findViewById(R.id.planContext);
                    Button button = (Button) layout.findViewById(R.id.timeSelect);
                    final String buttton_context=button.getText().toString();
                    final String textView_context=textView.getText().toString();

                    if (Monday.isChecked()) {
                        Plan plan = new Plan();
                        plan.setWeekday("2");
                        plan.setPlanContext(textView_context);
                        plan.setDaedlineTime(buttton_context);
                        plan.setPlanType("0");
                        plan.save();
                    }

                    if (Tuesday.isChecked())
                    {
                        Plan plan=new Plan();
                        plan.setWeekday("3");
                        plan.setPlanContext(textView_context);
                        plan.setDaedlineTime(buttton_context);
                        plan.setPlanType("0");
                        plan.save();
                    }

                    if (Wednesday.isChecked())
                    {
                        Plan plan=new Plan();
                        plan.setWeekday("4");
                        plan.setPlanContext(textView_context);
                        plan.setDaedlineTime(buttton_context);
                        plan.setPlanType("0");
                        plan.save();
                    }


                    if (Thursday.isChecked())
                    {
                        Plan plan=new Plan();
                        plan.setWeekday("5");
                        plan.setPlanContext(textView_context);
                        plan.setDaedlineTime(buttton_context);
                        plan.setPlanType("0");
                        plan.save();
                    }

                    if (Friday.isChecked())
                    {
                        Plan plan=new Plan();
                        plan.setWeekday("6");
                        plan.setPlanContext(textView_context);
                        plan.setDaedlineTime(buttton_context);
                        plan.setPlanType("0");
                        plan.save();
                    }
                    if (Saturday.isChecked())
                    {
                        Plan plan=new Plan();
                        plan.setWeekday("7");
                        plan.setPlanContext(textView_context);
                        plan.setDaedlineTime(buttton_context);
                        plan.setPlanType("0");
                        plan.save();
                    }
                    if (Sunday.isChecked())
                    {
                        Plan plan=new Plan();
                        plan.setWeekday("1");
                        plan.setPlanContext(textView_context);
                        plan.setDaedlineTime(buttton_context);
                        plan.setPlanType("0");
                        plan.save();
                    }
                }
                Intent intent=new Intent(planActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

}

