package com.mosbach.ld.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mosbach.ld.model.alexa.AlexaRO;
import com.mosbach.ld.services.AlexaService;

@RestController
@RequestMapping("/api/v0.1a/alexa")
public class AlexaController {

	private AlexaService alexaService;
	
	@Autowired
	public AlexaController(AlexaService alexaService) {
		this.alexaService = alexaService;
	}
	
	@PostMapping(
            path = "/endpoint",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
	public AlexaRO alexaEndPoint(@RequestBody AlexaRO alexaRO) {
		return alexaService.processRequest(alexaRO);
	}
}
