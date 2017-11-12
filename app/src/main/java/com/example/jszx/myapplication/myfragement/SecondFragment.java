package com.example.jszx.myapplication.myfragement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jszx.myapplication.MainAdapter;
import com.example.jszx.myapplication.Plan;
import com.example.jszx.myapplication.R;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by 王志杰 on 2017/11/9.
 */

public class SecondFragment extends Fragment {
    @Nullable
    private List<Plan> planList;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.second_fragment,null);
        RecyclerView recyclerView=(RecyclerView)view.findViewById(R.id.second_recyclerview);
        planList= DataSupport.where("isFinished=?","1").find(Plan.class);
        MainAdapter mainAdapter=new MainAdapter(planList);
        recyclerView.setAdapter(mainAdapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);
        return view;
    }
}
