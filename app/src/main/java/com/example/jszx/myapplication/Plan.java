package com.example.jszx.myapplication;

import org.litepal.crud.DataSupport;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by jszx on 2017/10/13.
 */

public class Plan extends DataSupport{
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String daedlineTime;
    private String planContext;
    private String Weekday;

    public String getWeekday() {
        return Weekday;
    }

    public void setWeekday(String weekday) {
        Weekday = weekday;
    }

    public String getPlanContext() {

        return planContext;
    }

    public void setPlanContext(String planContext) {
        this.planContext = planContext;
    }

    public String getDaedlineTime() {

        return daedlineTime;
    }

    public void setDaedlineTime(String daedlineTime) {
        this.daedlineTime = daedlineTime;
    }
}
