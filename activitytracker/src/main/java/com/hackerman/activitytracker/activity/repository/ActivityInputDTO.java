package com.hackerman.activitytracker.activity.repository;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalTime;


public class ActivityInputDTO {

    @NotNull
    @Size(min=3, max=128)
    private String name;

    @NotNull(message = "start timestamp cannot be null")
    private String startTimeStamp;

    @NotNull(message = "end timestamp cannot be null")
    private String endTimeStamp;

    private LocalDate date;
    private LocalTime time;

    public ActivityInputDTO(){}

    public ActivityInputDTO(String name, String startTimeStamp, String endTimeStamp) {
        this.endTimeStamp = endTimeStamp;
        this.startTimeStamp = startTimeStamp;
        this.name = name;
    }

    public ActivityInputDTO(String name, String startTimeStamp, String endTimeStamp, LocalDate date, LocalTime time) {
        this.name = name;
        this.startTimeStamp = startTimeStamp;
        this.endTimeStamp = endTimeStamp;
        this.date = date;
        this.time = time;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
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
