package com.nhn.android.beview.model;

import java.io.Serializable;

/**
 * Created by HyunSeungHo on 2017. 1. 12..
 */

public class Speaker implements Serializable{

    private int idx;
    private String name;
    private String img;
    private String email;
    private String phone;
    private String description;
    private int sectionIdx;

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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSectionIdx() {
        return sectionIdx;
    }

    public void setSectionIdx(int sectionIdx) {
        this.sectionIdx = sectionIdx;
    }
}
