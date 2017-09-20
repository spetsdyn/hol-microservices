package com.example.crudmovies.rest;


import com.example.crudmovies.cache.MovieClient;
import com.example.holentities.entity.Movie;

import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Stateless
@Path("movie")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Produces(MediaType.APPLICATION_JSON)
public class MovieFacadeREST {

	MovieClient mvClient = new MovieClient();

	@POST
	public Response create(Movie entity) {
		mvClient.put(Integer.parseInt(entity.getId()), entity);
		return Response.ok().build();
	}

	@PUT
	@Path("{id}")
	public Response edit(@PathParam("id") Integer id, Movie entity) {
		mvClient.edit(id, entity);
		return Response.ok().build();
	}

	@DELETE
	@Path("{id}")
	public void remove(@PathParam("id") Integer id) {
		mvClient.delete(id);
	}

	@GET
	@Path("{id}")
	public Movie find(@PathParam("id") Integer id) {
		return mvClient.get(id);
	}

	@GET
	public Response getAll() {
		return Response.ok(mvClient.getAll()).build();
	}

	@GET
	@Path("{page}/{limit}")
	public Response getAll(@PathParam("page") int page, @PathParam("limit") int limit) {
		return Response.ok(mvClient.getAll(page, limit)).build();
	}

	@GET
	@Path("count")
	@Produces("text/plain")
	public String countREST() {
		return String.valueOf(mvClient.count());
	}

}