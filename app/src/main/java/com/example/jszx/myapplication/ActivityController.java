package com.example.jszx.myapplication;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王志杰 on 2017/10/24.
 */

public class ActivityController {
    public static List<Activity> activities=new ArrayList<>();
    public static void addActivity(Activity activity)
    {
        activities.add(activity);
    }
    public static void removeActivity(Activity activity)
    {
        activities.remove(activity);
    }
    public static void finishAll()
    {
        for(Activity activity :activities)
        {
            if(!activity.isFinishing())
            {
                activity.finish();
            }
        }
    }
}
