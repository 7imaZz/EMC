package com.example.emc;

import android.net.Uri;

import java.net.URL;

public class GObject {
    private String name;
    private String bornDie;
    private String details;
    private String imgId;
    private String id;

    public GObject() {
    }

    public GObject(String name, String bornDie, String details, String imgId, String id) {
        this.name = name;
        this.bornDie = bornDie;
        this.details = details;
        this.imgId = imgId;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getBornDie() {
        return bornDie;
    }

    public String getDetails() {
        return details;
    }

    public String getImgId() {
        return imgId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBornDie(String bornDie) {
        this.bornDie = bornDie;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
