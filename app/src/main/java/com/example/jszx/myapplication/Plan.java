package com.example.jszx.myapplication;

import org.litepal.crud.DataSupport;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by jszx on 2017/10/13.
 */

public class Plan extends DataSupport{

    private int id;
    private String daedlineTime;     //计划的截止时间
    private String planContext;     //计划的内容
    private String Weekday;         //计划的星期数
    private String planType;        //计划的种类（为0时为每日计划，为1时为备忘）
    private int position;           //图片存储中区分不同图片的参数
    private String isFinished="0";  //计划是否已完成

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
