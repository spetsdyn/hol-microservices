package com.example.holgui.booking.client;

import com.example.holentities.entity.Booking;
import com.example.holentities.entity.MovieProgram;
import com.example.holentities.entity.ShowTiming;
import com.example.holgui.booking.json.ShowTimingWriter;

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

/**
 * Created by spetsiotis on 2/28/17.
 */
@Named
@RequestScoped
public class ShowTimingClientBean {
    @Inject
    ShowTimingBackingBean bean;

    Client client;
    WebTarget target;

    //	@Inject
    //	HttpServletRequest httpServletRequest;

    @PostConstruct
    public void init() {
        client = ClientBuilder.newClient();
        target = client.target("http://10.250.21.115:8090/booking");
    }

    @PreDestroy
    public void destroy() {
        client.close();
    }

    public Integer[] getShowDays() {
        return target.path("{movieId}").resolveTemplate("movieId", bean.getMovieId()).request().get(Integer[].class);
    }

    public Integer[] getTimeslot() {
        return target.path("{movieId}/{day}").resolveTemplate("movieId", bean.getMovieId())
                .resolveTemplate("day", bean.getDay()).request().get(Integer[].class);
    }

    public void setTheater() {
        Integer theater = target.path("{movieId}/{day}/{time}").resolveTemplate("movieId", bean.getMovieId())
                .resolveTemplate("day", bean.getDay()).resolveTemplate("time", bean.getStartTime()).request()
                .get(Integer.class);
        System.out.println("#####################################");
        if (theater != null) System.out.println("+++++++++ " + theater + " +++++++");
        else System.out.println("+++++++++ null +++++++");
        bean.setTheater(theater);
    }

    public ShowTiming[] getShowTimings() {
        return target.path("{movieId}").resolveTemplate("movieId", bean.getMovieId()).request().get(ShowTiming[].class);
    }

    public void addShowTiming() {
        MovieProgram mp = new MovieProgram();
        mp.setMovieId(bean.getMovieId());
        System.out.println("" + bean.getMovieId());
        mp.setDay(bean.getDay());
        System.out.println("" + bean.getDay());
        mp.setStartTime(bean.getStartTime());
        System.out.println("" + bean.getStartTime());
        Booking booking = new Booking();
        booking.setEndTime(bean.getEndTime());
        booking.setHall(bean.getTheater());
        booking.setCapacity(bean.getCapacity());
        ShowTiming showTiming = new ShowTiming();
        showTiming.setMp(mp);
        showTiming.setBooking(booking);
        System.out.println(
                "##########################################\n" + "##########################################\n" +
                        "##########################################\n" +
                        "##########################################\n" + showTiming
                        .toString() + "##########################################\n");
        target.register(ShowTimingWriter.class).request().post(Entity.entity(showTiming, MediaType.APPLICATION_JSON));
    }

}
