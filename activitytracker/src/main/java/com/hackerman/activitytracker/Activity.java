package com.hackerman.activitytracker;

public class Activity {

    private String name;
    private String startTimeStamp;
    private String endTimeStamp;

    public Activity(String name, String startTimeStamp, String endTimeStamp) {
        this.name = name;
        this.startTimeStamp = startTimeStamp;
        this.endTimeStamp = endTimeStamp;
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
        return "Activity {" +
                "\n\t\tname='" + name + '\'' +
                ",\n\t\tstartTimeStamp='" + startTimeStamp + '\'' +
                ",\n\t\tendTimeStamp='" + endTimeStamp + '\'' +
                "\n\t}";
    }
}
