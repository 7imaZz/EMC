package com.example.emc;

public class Course {
    private String name;
    private String date;
    private String requirements;
    private String id;
    private String cost;

    public Course() {
    }

    public Course(String name, String date, String requirements, String id) {
        this.name = name;
        this.date = date;
        this.requirements = requirements;
        this.id = id;
    }

    public Course(String name, String date, String requirements, String id, String cost) {
        this.name = name;
        this.date = date;
        this.requirements = requirements;
        this.id = id;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}
