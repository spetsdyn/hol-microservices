package com.example.booking.rest;

import com.example.booking.cache.BookingClient;
import com.example.holentities.entity.ShowTiming;

import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;

/**
 * Created by spetsiotis on 2/28/17.
 */
@Stateless
@Path("booking")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces(MediaType.APPLICATION_JSON)
public class BookingFacadeREST {

    private static final Logger LOG = Logger.getLogger(BookingFacadeREST.class);
    BookingClient bClient = new BookingClient();

    @POST
    public Response create(ShowTiming st) {
        LOG.info("create method was invoked.");
        bClient.put(st.getMp(), st.getBooking());
        return Response.ok().build();
    }

    @GET
    @Path("{movieId}")
    public Response getDay(@PathParam("movieId") int movieId) {
        LOG.info("getDay method was invoked.");
        return Response.ok(bClient.getDay(movieId)).build();
    }

    @GET
    @Path("{movieId}/{day}")
    public Response getDay(@PathParam("movieId") int movieId, @PathParam("day") int day) {
        LOG.info("getDay method was invoked.");
        return Response.ok(bClient.getTimeslot(movieId, day)).build();
    }

//    @GET
//    @Path("{movieId}/{day}/{time}")
//    public Response getBooking(@PathParam("movieId") int movieId, @PathParam("day") int day, @PathParam("time") int time) {
//        return Response.ok(bClient.getBooking(movieId, day, time)).build();
//    }
    @GET
    @Path("{movieId}/{day}/{time}")
    public Response getTheater(@PathParam("movieId") int movieId, @PathParam("day") int day, @PathParam("time") int time) {
        LOG.info("getTheater method was invoked.");
        return Response.ok(bClient.getTheater(movieId, day, time)).build();
    }

    @GET
    public Response getAll() {
        LOG.info("getAll method was invoked.");
        return Response.ok(bClient.getAll()).build();
    }

}
