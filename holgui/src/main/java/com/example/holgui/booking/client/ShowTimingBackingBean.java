package com.example.holgui.booking.client;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by spetsiotis on 2/28/17.
 */
@Named
@SessionScoped
public class ShowTimingBackingBean implements Serializable {

    private Integer movieId;
    private Integer day;
    Integer startTime;
    Integer theater;
    Integer endTime;
    Integer capacity;

    public ShowTimingBackingBean() {
    }

    public ShowTimingBackingBean(Integer movieId, Integer day, Integer startTime, Integer endTime, Integer theater,
                                 Integer capacity) {
        this.movieId = movieId;
        this.day = day;
        this.startTime = startTime;
        this.theater = theater;
        this.endTime = endTime;
        this.capacity = capacity;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    public Integer getTheater() {
        return theater;
    }

    public void setTheater(Integer theater) {
        this.theater = theater;
    }

    //    public void setTheaterRest() {
    //        ShowTimingClientBean client = new ShowTimingClientBean();
    //        if(client.getTheater()!=null) {
    //            System.out.println(client);
    //            if(client.getTheater()!=null) {
    //                this.theater = client.getTheater();
    //                System.out.println(this.theater);
    //            }
    //        }else this.theater = 0;
    //    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
}

