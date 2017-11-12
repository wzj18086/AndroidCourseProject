package com.example.jszx.myapplication.myfragement;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by 王志杰 on 2017/11/9.
 */

public class MyFragmentAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragments;
    public MyFragmentAdapter(FragmentManager fragmentManager, List<Fragment> fragmentList)
    {
        super(fragmentManager);
        this.fragments=fragmentList;
    }
    @Override
    public Fragment getItem(int position) {
        if(fragments==null || fragments.size()==0)
        {
            return null;
        }else
            return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }
}
