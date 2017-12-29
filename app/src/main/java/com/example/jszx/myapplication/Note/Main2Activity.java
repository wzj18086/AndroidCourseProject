package com.example.jszx.myapplication.Note;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.jszx.myapplication.R;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    private List<Content> contentList;
    private NoteAdapter noteAdapter;
    private IntentFilter intentFilter;
    private MyBroadcastReceiver myBroadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar_2);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        contentList=new ArrayList<>();
        contentList= DataSupport.findAll(Content.class);
        RecyclerView NoteRecyclerview=(RecyclerView) findViewById(R.id.NoteRecyclerView);
        noteAdapter=new NoteAdapter(contentList);
        NoteRecyclerview.setAdapter(noteAdapter);



        intentFilter=new IntentFilter();
        intentFilter.addAction("com.example.edittextpractise.MY_BROADCAST");
        myBroadcastReceiver=new MyBroadcastReceiver();
        registerReceiver(myBroadcastReceiver,intentFilter);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        NoteRecyclerview.setLayoutManager(linearLayoutManager);

        //floatingActionButton的点击响应事件，进行笔记的添加
        com.github.clans.fab.FloatingActionButton floatingActionButton=(com.github.clans.fab.FloatingActionButton) findViewById(R.id.addNote);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Main2Activity.this,MainActivity.class);
                intent.putExtra("note_position",noteAdapter.getItemCount());
                startActivity(intent);
            }
        });
    }
    class MyBroadcastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            contentList.clear();
            List<Content> contents=new ArrayList<>();
            contents=DataSupport.findAll(Content.class);
            contentList.addAll(contents);
            noteAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myBroadcastReceiver);
    }
}
