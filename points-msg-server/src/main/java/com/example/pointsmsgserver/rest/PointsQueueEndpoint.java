package com.example.pointsmsgserver.rest;

import com.example.pointsmsgserver.point.ReceivePointsBean;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("/point")
public class PointsQueueEndpoint {

    @EJB
    ReceivePointsBean receivePointsBean;

    @GET
    @Produces("text/plain")
    public Response getQueueSize() {
        int queueSize = receivePointsBean.getQueueSize();
        return Response.ok(queueSize).build();
    }

    @GET
    @Produces("text/plain")
    public Response receiveMessage() {
        String message = receivePointsBean.receiveMessage();
        return Response.ok(message).build();
    }
}
