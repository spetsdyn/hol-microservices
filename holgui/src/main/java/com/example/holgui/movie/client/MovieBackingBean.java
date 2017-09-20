package com.example.holgui.movie.client;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by spetsiotis on 2/23/17.
 */
@Named
@SessionScoped
public class MovieBackingBean implements Serializable {

    String movieId;
    String movieName;
    String actors;

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String mid) {
        this.movieId = mid;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }
}
