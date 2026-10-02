package com.hackerman.activitytracker.activity.repository;


public interface ActivityOutputDTO {
    Long getEntityId();
    String getName();
    String getStartTimeStamp();
    String getEndTimeStamp();
}
