package com.example.schedule_1;

public class Schedule_item {
    String time;
    String days;

    public Schedule_item(String time, String days){
        this.time = time;
        this.days = days;
    }

    public String getTime(){
        return time;
    }

    public String getDays(){
        return days;
    }

    public void setTime(String time){
        this.time = time;
    }

    public void setDays(String days){
        this.days = days;
    }
}