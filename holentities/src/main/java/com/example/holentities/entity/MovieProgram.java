package com.example.holentities.entity;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.io.Serializable;

/**
 * Created by spetsiotis on 2/24/17.
 */
public class MovieProgram implements Serializable {

    @QuerySqlField
    Integer movieId;
    @QuerySqlField
    Integer day;
    @QuerySqlField
    Integer startTime;

    public MovieProgram() {
    }

    public MovieProgram(Integer movieId, Integer day, Integer startTime) {
        this.movieId = movieId;
        this.day = day;
        this.startTime = startTime;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MovieProgram that = (MovieProgram) o;

        if (!movieId.equals(that.movieId)) return false;
        if (!day.equals(that.day)) return false;
        return startTime.equals(that.startTime);
    }

    @Override
    public int hashCode() {
        int result = movieId.hashCode();
        result = 31 * result + day.hashCode();
        result = 31 * result + startTime.hashCode();
        return result;
    }
}
