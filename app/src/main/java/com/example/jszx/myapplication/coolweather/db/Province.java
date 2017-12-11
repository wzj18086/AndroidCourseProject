package com.example.jszx.myapplication.coolweather.db;

import org.litepal.crud.DataSupport;

/**
 * Created by lenovo on 2017/11/30.
 */
//存放省的数据信息
public class Province extends DataSupport {
    private int id;//每个实体类都应该有的字段

    private String provinceName;//省的名字

    private int provinceCode;//省的代号

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id ;
    }

    public String getProvinceName(){
        return provinceName;
    }

    public void setProvinceName(String provinceName){
        this.provinceName = provinceName;
    }

    public int getProvinceCode(){
        return provinceCode;
    }

    public void setProvinceCode(int provinceCode){
        this.provinceCode = provinceCode;
    }

}
