package com.garantis.polarion.extension.license.analyzer.rest.controller;

import com.garantis.polarion.extension.license.analyzer.store.repository.SessionRepository;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//@Hidden
@Path("/internal")
@Tag(
   name = "Global parameters"
)
@OpenAPIDefinition(
   info = @Info(
   title = "License Analyzer REST API"
)
)
@Component
public class GlobalInternalController {

	@Autowired
	private SessionRepository sessionRepository;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
    @Path("/sessions")
	public Response getSessions() {
		return Response.ok(sessionRepository.findAll()).build();
	}

}
