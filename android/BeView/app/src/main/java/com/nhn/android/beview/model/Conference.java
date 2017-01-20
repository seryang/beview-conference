package com.nhn.android.beview.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by HyunSeungHo on 2017. 1. 10..
 */

public class Conference implements Serializable{

    private int idx;
    private String strId = "";
    private String name = "";
    private Date startDate;
    private Date endDate;
    private String location = "";
    private final SimpleDateFormat simpleDateFormat;

    public Conference() {
        simpleDateFormat = new SimpleDateFormat("yy.MM.dd");
    }

    public void setStrId(String strId) {
        this.strId = strId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getStrId() {
        return strId;
    }

    public String getName() {
        return name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getLocation() {
        return location;
    }

    public String getStringStartDate() {
        return simpleDateFormat.format(startDate);
    }

    public String getStringEndDate() {
        return simpleDateFormat.format(endDate);
    }
}
