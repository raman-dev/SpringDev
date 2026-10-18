package com.hackerman.activitytracker.activity.repository;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;


public class ActivityInputDTO {

    @NotNull
    @Size(min=3, max=128)
    private String name;

    @NotNull
    private LocalDate date;

    @NotNull
    @JsonFormat(pattern="HH:mm")
    private LocalTime startTime;

    @NotNull
    @JsonFormat(pattern="HH:mm")
    private LocalTime endTime;

    @NotNull
    private TimeZone timeZone;

    public ActivityInputDTO(){};

    public ActivityInputDTO(String name, LocalDate date, LocalTime startTime, LocalTime endTime,TimeZone timeZone) {
        this.name = name;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.timeZone = timeZone;
    }


    public String getStartTimeIso8601(){
        return toISO8601(this.date,this.startTime,this.timeZone);
    }

    public String getEndTimeIso8601(){
        return toISO8601(this.date,this.endTime,this.timeZone);
    }

    private String toISO8601(LocalDate localDate, LocalTime localTime, TimeZone timeZone) {
        return ZonedDateTime.of(localDate,localTime,timeZone.toZoneId())
                .format(DateTimeFormatter.ISO_INSTANT);//returns date time in specified format
    }


    public TimeZone getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}
