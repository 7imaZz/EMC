package com.example.emc;

public class Event {
    private String name;
    private String dayNumber;
    private String month;
    private String dayName;
    private String time;
    private int peopleNumber;
    private String description;
    private int going = -1;
    private String id;



    public Event(String name, String dayNumber, String month, String dayName, String time, int peopleNumber, String description) {
        this.name = name;
        this.dayNumber = dayNumber;
        this.month = month;
        this.dayName = dayName;
        this.time = time;
        this.peopleNumber = peopleNumber;
        this.description = description;
    }

    public Event() {
    }

    public Event(String name, String dayNumber, String month, String dayName, String time, int peopleNumber, String description, String id) {
        this.name = name;
        this.dayNumber = dayNumber;
        this.month = month;
        this.dayName = dayName;
        this.time = time;
        this.peopleNumber = peopleNumber;
        this.description = description;
        this.id = id;
    }

    public Event(String name, String dayNumber, String month, String dayName, int peopleNumber, String description) {
        this.name = name;
        this.dayNumber = dayNumber;
        this.month = month;
        this.dayName = dayName;
        this.peopleNumber = peopleNumber;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(String dayNumber) {
        this.dayNumber = dayNumber;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public int getPeopleNumber() {
        return peopleNumber;
    }

    public void setPeopleNumber(int peopleNumber) {
        this.peopleNumber = peopleNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getGoing() {
        return going;
    }

    public void setGoing(int going) {
        this.going = going;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
