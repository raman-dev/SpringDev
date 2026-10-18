package com.hackerman.activitytracker.activity.repository;

import com.hackerman.activitytracker.activity.Activity;

import java.util.Objects;

public record ActivityResponseBody(Long entityId, String name, String startTimeStamp, String endTimeStamp) {
    public static ActivityResponseBody fromActivity(Activity activity) {
        Objects.requireNonNull(activity);
        return new ActivityResponseBody(activity.getEntityId(), activity.getName(), activity.getStartTimeStamp(), activity.getEndTimeStamp());
    }
}
