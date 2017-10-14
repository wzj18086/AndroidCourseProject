package com.example.jszx.myapplication;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class planActivity extends AppCompatActivity {
    private List<Plan> mplanList=new ArrayList<>();
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plan);
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.planRecyclerView);
        planAdapter=new PlanAdapter(mplanList);
        recyclerView.setAdapter(planAdapter);
        final LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        FloatingActionButton floatingActionButton=(FloatingActionButton)findViewById(R.id.addNewPlan);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                planAdapter.add(new Plan(),planAdapter.getItemCount());
            }
        });
        Monday=(CheckBox)findViewById(R.id.Monday);
        Tuesday=(CheckBox)findViewById(R.id.Tuesday);
        Wednesday=(CheckBox)findViewById(R.id.Wednsday);
        Thursday=(CheckBox)findViewById(R.id.Thusday);
        Friday=(CheckBox)findViewById(R.id.Friday);
        Saturday=(CheckBox)findViewById(R.id.Saturday);
        Sunday=(CheckBox)findViewById(R.id.Sunday);
        ensureData=(Button)findViewById(R.id.ensure);

        ensureData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View view1=layoutManager.findViewByPosition(0);
                LinearLayout layout=(LinearLayout)view1;
                TextView textView=(TextView)layout.findViewById(R.id.planContext);
                Toast.makeText(planActivity.this,textView.getText().toString(),Toast.LENGTH_SHORT).show();
            }
        });

    }
}
