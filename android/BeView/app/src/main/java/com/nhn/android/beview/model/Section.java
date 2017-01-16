package com.nhn.android.beview.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by HyunSeungHo on 2017. 1. 12..
 */

public class Section implements Serializable {
    private int idx;
    private String name;
    private String description;
    private String file;
    private Date startTime;
    private Date endTime;
    private int trackIdx;
    private String trackName = "";
    private int speakerIdx;
    private String speakerName = "";
    private final SimpleDateFormat simpleDateFormat;

    public Section() {
        simpleDateFormat = new SimpleDateFormat("hh:mm");
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getTrackIdx() {
        return trackIdx;
    }

    public void setTrackIdx(int trackIdx) {
        this.trackIdx = trackIdx;
    }

    public int getSpeakerIdx() {
        return speakerIdx;
    }

    public void setSpeakerIdx(int speakerIdx) {
        this.speakerIdx = speakerIdx;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getSpeakerName() {
        return speakerName;
    }

    public void setSpeakerName(String speakerName) {
        this.speakerName = speakerName;
    }

    public String getStringStartTime() {
        return simpleDateFormat.format(startTime);
    }

    public String getStringEndTime() {
        return simpleDateFormat.format(endTime);
    }
}
