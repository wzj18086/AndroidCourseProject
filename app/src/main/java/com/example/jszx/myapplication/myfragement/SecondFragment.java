package com.example.jszx.myapplication.myfragement;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.jszx.myapplication.MainAdapter;
import com.example.jszx.myapplication.Plan;
import com.example.jszx.myapplication.R;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王志杰 on 2017/11/9.
 */
//已完成界面，这里的计划事项和备忘事项的isFinished应该全部为1
public class SecondFragment extends Fragment{
    @Nullable
    private List<Plan> planList=new ArrayList<>();
    private MainAdapter mainAdapter;
    private SwipeRefreshLayout refreshLayout;
    private IntentFilter intentFilter;
    private LocalReciever localReciever;
    private LocalBroadcastManager localBroadcastManager;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.second_fragment,null);
        localBroadcastManager=LocalBroadcastManager.getInstance(this.getActivity());
        RecyclerView recyclerView=(RecyclerView)view.findViewById(R.id.second_recyclerview);
        planList= DataSupport.where("isFinished=? ","1").find(Plan.class);//获取isFinished为1的所有事项（如果不是今天的，在未完成界面已经将isFinished全部置0，所以只会查询到今天的已完成事项）
        mainAdapter=new MainAdapter(planList);
        recyclerView.setAdapter(mainAdapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);
        refreshLayout=(SwipeRefreshLayout) view.findViewById(R.id.second_refresh);
        //下拉刷新
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                List<Plan> temp=DataSupport.where("isFinished=?","1").find(Plan.class);
                planList.clear();
                planList.addAll(temp);
                mainAdapter.notifyDataSetChanged();
                refreshLayout.setRefreshing(false);
            }
        });
        intentFilter=new IntentFilter();
        intentFilter.addAction("MainFragment");
        LocalReciever localReciever=new LocalReciever();
        localBroadcastManager.registerReceiver(localReciever,intentFilter);
        return view;
    }
    //已完成界面上收到未完成界面删除条目发出的广播之后，进行界面更新的操作
    class LocalReciever extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            List<Plan> temp=DataSupport.where("isFinished=?","1").find(Plan.class);
            planList.clear();
            planList.addAll(temp);
            mainAdapter.notifyDataSetChanged();
            refreshLayout.setRefreshing(false);
        }
    }


}
