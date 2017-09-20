package com.example.holentities.entity;

import java.io.Serializable;

/**
 * Created by spetsiotis on 2/28/17.
 */
public class ShowTiming implements Serializable {
    private MovieProgram mp;
    private Booking booking;

    //    private Integer movieId;
    //    private Integer day;
    //    private Integer startTime;
    //    private Integer hall;
    //    private Integer endTime;
    //    private Integer capacity;
    //
    public ShowTiming() {
    }

    //
    //    public ShowTiming(MovieProgram mp, Booking booking) {
    //        this.movieId = mp.getMovieId();
    //        this.day = mp.getDay();
    //        this.startTime = mp.getStartTime();
    //        this.hall = booking.getHall();
    //        this.endTime = booking.getEndTime();
    //        this.capacity = booking.getCapacity();
    //    }
    //
    //    public Integer getMovieId() {
    //        return movieId;
    //    }
    //
    //    public void setMovieId(Integer movieId) {
    //        this.movieId = movieId;
    //    }
    //
    //    public Integer getDay() {
    //        return day;
    //    }
    //
    //    public void setDay(Integer day) {
    //        this.day = day;
    //    }
    //
    //    public Integer getStartTime() {
    //        return startTime;
    //    }
    //
    //    public void setStartTime(Integer startTime) {
    //        this.startTime = startTime;
    //    }
    //
    //    public Integer getHall() {
    //        return hall;
    //    }
    //
    //    public void setHall(Integer hall) {
    //        this.hall = hall;
    //    }
    //
    //    public Integer getEndTime() {
    //        return endTime;
    //    }
    //
    //    public void setEndTime(Integer endTime) {
    //        this.endTime = endTime;
    //    }
    //
    //    public Integer getCapacity() {
    //        return capacity;
    //    }
    //
    //    public void setCapacity(Integer capacity) {
    //        this.capacity = capacity;
    //    }
    public ShowTiming(MovieProgram mp, Booking booking) {
        this.mp = mp;
        this.booking = booking;
    }

    public MovieProgram getMp() {
        return mp;
    }

    public void setMp(MovieProgram mp) {
        this.mp = mp;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }
}
