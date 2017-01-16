package com.nhn.android.beview.model;

import java.io.Serializable;

/**
 * Created by HyunSeungHo on 2017. 1. 12..
 */

public class Track implements Serializable{

    private int idx;
    private String name;
    private String location;
    private int conferenceIdx;

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getConferenceIdx() {
        return conferenceIdx;
    }

    public void setConferenceIdx(int conferenceIdx) {
        this.conferenceIdx = conferenceIdx;
    }
}
