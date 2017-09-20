package com.example.holentities.entity;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.io.Serializable;

/**
 * Created by spetsiotis on 2/23/17.
 */
public class Movie implements Serializable {

    @QuerySqlField
    private String id;
    @QuerySqlField
    private String name;
    @QuerySqlField
    private String actors;

    public Movie() {
    }

    public Movie(String id, String name, String actors) {
        this.id = id;
        this.name = name;
        this.actors = actors;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }
}
