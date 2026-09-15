package com.hackerman.activitytracker.activity.repository;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.time.LocalTime;


public class ActivityInputDTO {


    @NotNull
    @Size(min=3, max=128)
    private String name;

    @NotNull
    private String startTimeStamp;

    @NotNull
    private String endTimeStamp;


    private LocalDateTime date;
    private LocalTime time;

    public ActivityInputDTO(String name, String startTimeStamp, String endTimeStamp) {
        this.endTimeStamp = endTimeStamp;
        this.startTimeStamp = startTimeStamp;
        this.name = name;
    }

    public ActivityInputDTO(String name, String startTimeStamp, String endTimeStamp, LocalDateTime date, LocalTime time) {
        this.name = name;
        this.startTimeStamp = startTimeStamp;
        this.endTimeStamp = endTimeStamp;
        this.date = date;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTimeStamp() {
        return startTimeStamp;
    }

    public void setStartTimeStamp(String startTimeStamp) {
        this.startTimeStamp = startTimeStamp;
    }

    public String getEndTimeStamp() {
        return endTimeStamp;
    }

    public void setEndTimeStamp(String endTimeStamp) {
        this.endTimeStamp = endTimeStamp;
    }

    @Override
    public String toString() {
        return "ActivityInputDTO{" +
                "\n\t\tendTimeStamp='" + endTimeStamp + '\'' +
                ",\n\t\tstartTimeStamp='" + startTimeStamp + '\'' +
                ",\n\t\tname='" + name + '\'' +
                '}';
    }
}
