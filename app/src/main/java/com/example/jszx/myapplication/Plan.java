package com.example.jszx.myapplication;

import org.litepal.crud.DataSupport;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by jszx on 2017/10/13.
 */

public class Plan extends DataSupport{

    private int id;
    private String daedlineTime;
    private String planContext;
    private String Weekday;
    private String planType;
    private int position;
    private String isFinished="0";

    public String isFinished() {
        return isFinished;
    }

    public void setFinished(String finished) {
        isFinished = finished;
    }



    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

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
