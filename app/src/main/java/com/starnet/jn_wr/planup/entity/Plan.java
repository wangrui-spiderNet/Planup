package com.starnet.jn_wr.planup.entity;

/**
 * Created by jn-wr on 2016/5/3.
 */
public class Plan {
    private String id;
    private String acttime;
    private String ct;
    private String content;
    private String repeatDays;
    private int type;

    public String getCt() {
        return ct;
    }

    public void setCt(String ct) {


        this.ct = ct;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRepeatDays() {
        return repeatDays;
    }

    public void setRepeatDays(String repeatDays) {
        this.repeatDays = repeatDays;
    }

    public String getActtime() {
        return acttime;
    }

    public void setActtime(String acttime) {
        this.acttime = acttime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
