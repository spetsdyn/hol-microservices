package com.example.holentities.entity;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.io.Serializable;

/**
 * Created by spetsiotis on 2/24/17.
 */
public class Booking implements Serializable {

    @QuerySqlField
    private Integer hall;
    @QuerySqlField
    private Integer endTime;
    @QuerySqlField
    private Integer capacity;

    public Booking() {
    }

    public Booking(Integer hall, Integer endTime, Integer capacity) {
        this.hall = hall;
        this.endTime = endTime;
        this.capacity = capacity;
    }

    public Integer getHall() {
        return hall;
    }

    public void setHall(Integer hall) {
        this.hall = hall;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
}
