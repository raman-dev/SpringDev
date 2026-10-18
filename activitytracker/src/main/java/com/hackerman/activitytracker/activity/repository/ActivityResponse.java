package com.hackerman.activitytracker.activity.repository;

import com.hackerman.activitytracker.activity.Activity;

import java.util.Objects;

public class ActivityResponse{
    private final Long entityId;
    private final String name;
    private final String startTimeStamp;
    private final String endTimeStamp;

    public ActivityResponse(Activity activity){
        Objects.requireNonNull(activity);
        this.entityId = activity.getEntityId();
        this.startTimeStamp = activity.getStartTimeStamp();
        this.endTimeStamp = activity.getEndTimeStamp();
        this.name = activity.getName();
    }

    public ActivityResponse(Long entityId, String name, String startTimeStamp, String endTimeStamp) {
        this.entityId = entityId;
        this.name = name;
        this.startTimeStamp = startTimeStamp;
        this.endTimeStamp = endTimeStamp;
    }

    @Override
    public String toString() {
        return "ActivityResponse{" +
                "entityId=" + entityId +
                ", name='" + name + '\'' +
                ", startTimeStamp='" + startTimeStamp + '\'' +
                ", endTimeStamp='" + endTimeStamp + '\'' +
                '}';
    }
}

