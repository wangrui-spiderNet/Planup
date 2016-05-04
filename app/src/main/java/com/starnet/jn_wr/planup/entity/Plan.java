package com.starnet.jn_wr.planup.entity;

/**
 * Created by jn-wr on 2016/5/3.
 */
public class Plan {
    private String acttime;
    private String content;
    private String repeatDays;
    private boolean iscomplete;

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

    public String getRepeatDays() {
        return repeatDays;
    }

    public void setRepeatDays(String repeatDays) {
        this.repeatDays = repeatDays;
    }
}
