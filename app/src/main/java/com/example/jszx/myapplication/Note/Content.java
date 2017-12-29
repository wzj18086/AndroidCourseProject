package com.example.jszx.myapplication.Note;

import org.litepal.crud.DataSupport;

/**
 * Created by 王志杰 on 2017/12/9.
 */

public class Content extends DataSupport {
    private int id;
    private String content; //读书笔记的内容
    private String titleText;   //读书笔记的标题
    private Integer position;    //存储图片的辨识位
    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }


    public String getTitleText() {
        return titleText;
    }

    public void setTitleText(String titleText) {
        this.titleText = titleText;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
