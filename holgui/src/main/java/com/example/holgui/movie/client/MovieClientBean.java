package com.example.holgui.movie.client;

import com.example.holentities.entity.Movie;
import com.example.holgui.movie.json.MovieWriter;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

@Named
@RequestScoped
public class MovieClientBean {

    @Inject
    MovieBackingBean bean;

    Client client;
    WebTarget target;

    //	@Inject
    //	HttpServletRequest httpServletRequest;

    @PostConstruct
    public void init() {
        client = ClientBuilder.newClient();
        target = client.target("http://10.250.21.115:8080/movie");
        //						httpServletRequest.getLocalName() +
        //						":" +
        //						httpServletRequest.getLocalPort() +
        //						"/" +
        //						httpServletRequest.getContextPath() +
        //						"/movie/");
    }

    @PreDestroy
    public void destroy() {
        client.close();
    }

    public Movie[] getMovies() {
        return target.request().get(Movie[].class);
    }

    public Movie getMovie() {
        Movie m = target.path("{movie}").resolveTemplate("movie", bean.getMovieId()).request().get(Movie.class);
        return m;
    }

    public Movie getMovieJson() {
        Movie m = target.path("{movie}").resolveTemplate("movie", bean.getMovieId()).request(MediaType.APPLICATION_JSON)
                .get(Movie.class);
        return m;
    }

    public void addMovie() {
        Movie m = new Movie();
        m.setId(bean.getMovieId());
        m.setName(bean.getMovieName());
        m.setActors(bean.getActors());
        target.register(MovieWriter.class).request().post(Entity.entity(m, MediaType.APPLICATION_JSON));
    }

    public void deleteMovie() {
        target.path("{movieId}").resolveTemplate("movieId", bean.getMovieId()).request().delete();
    }
}
