package com.example.jszx.myapplication.coolweather.gson;

/**
 * Created by lenovo on 2017/12/4.
 */

public class AQI {
    public AQICity city;

    public class AQICity{
        public String aqi;

        public String pm25;
    }
}
