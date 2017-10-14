package com.example.jszx.myapplication;

import java.util.Date;

/**
 * Created by jszx on 2017/10/13.
 */

public class Plan {
    private Date daedlineTime;
    private String planContext;
    private Date Weekday;

    public Date getWeekday() {
        return Weekday;
    }

    public void setWeekday(Date weekday) {
        Weekday = weekday;
    }

    public String getPlanContext() {

        return planContext;
    }

    public void setPlanContext(String planContext) {
        this.planContext = planContext;
    }

    public Date getDaedlineTime() {

        return daedlineTime;
    }

    public void setDaedlineTime(Date daedlineTime) {
        this.daedlineTime = daedlineTime;
    }
}
