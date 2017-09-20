package com.example.pointsmsgclient.rest;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("/hello")
public class PointsClientEndpoint {

    @GET
    @Produces("text/plain")
    public Response doGet() {
        return Response.ok("Hello from WildFly Swarm!").build();
    }

    private boolean sendMessageToQueue(String message, String url) {
        boolean result = true;
        try {
            //            StringWriter sw = new StringWriter();
            //            JAXB.marshal(message, sw);
            //           System.out.println("The message is \n"+sw.toString());

            ResteasyClient client = new ResteasyClientBuilder().build();
            ResteasyWebTarget target = client.target(url);
            Response response = target.request().post(Entity.entity(message, MediaType.TEXT_XML));

            //Read output in string format
            int status = response.getStatus();
            System.out.println(response.getStatus());
            response.close();

            //        if (res.getStatus() == 307) {
            //            Link redirect = res.getLocationLink();
            //            System.out.println("Redirecting to "+redirect.getHref());
            //            res.releaseConnection();
            //            res = redirect.request().body( MediaType.TEXT_XML, content).post();
            //        }

            if (status != 201) {
                throw new RuntimeException("Failed to post");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            result = false;
        }
        return result;

    }
}
