package com.hackerman.activitytracker.activity;

import com.hackerman.activitytracker.user.MyUser;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="activity")
public class Activity {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long entityId;

    @Column(name="name")
    private String name;

    //ensure timestamps are in iso 8601 format
    @Column(name="start_time_stamp")
    private String startTimeStamp;

    @Column(name="end_time_stamp")
    private String endTimeStamp;

    @ManyToOne
    @JoinColumn(name="id",referencedColumnName = "id")
    //name -> name of column in activity table
    //referencedColumnName -> name of column in MyUser Table
    //jpa will pick up that MyUser is an entity
    private MyUser owner;

    public Activity (){}

    public Activity(String name, String startTimeStamp, String endTimeStamp) {
        this.name = name;
        this.startTimeStamp = startTimeStamp;
        this.endTimeStamp = endTimeStamp;
    }

    public MyUser getOwner() {
        return owner;
    }

    public Long getEntityId(){
        return entityId;
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
