package com.example.jszx.myapplication;

/**
 * Created by 王志杰 on 2017/10/17.
 */

public class String_to_time {
    public static String Translate (int time)
    {
        if(time < 10)
        {
            return "0"+String.valueOf(time);
        }else
            return String.valueOf(time);
    }
}
