package com.example.emc;

import android.net.Uri;

import java.net.URL;

public class GObject {
    private String name;
    private String bornDie;
    private String details;
    private Uri imgId;

    public GObject(String name, String bornDie, String details, Uri imgId) {
        this.name = name;
        this.bornDie = bornDie;
        this.details = details;
        this.imgId = imgId;
    }

    public GObject(String name, String bornDie, String details) {
        this.name = name;
        this.bornDie = bornDie;
        this.details = details;
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

    public Uri getImgId() {
        return imgId;
    }
}
