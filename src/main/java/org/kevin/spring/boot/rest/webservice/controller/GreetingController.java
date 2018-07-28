package org.kevin.spring.boot.rest.webservice.controller;

import java.net.URI;
import java.util.concurrent.atomic.AtomicLong;

import org.kevin.spring.boot.rest.webservice.model.Greeting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

	private static final String TEMPLATE = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
	private static final Logger LOGGER = LoggerFactory.getLogger(GreetingController.class);
	
	@Autowired
	private URI webServiceFetchURI;

	@Autowired
	private URI webServiceUpdateURI;

	@RequestMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		LOGGER.info("web service fetch uri: {}", webServiceFetchURI);
		LOGGER.info("web service update uri: {}", webServiceUpdateURI);
		LOGGER.debug("Debug Message");
		LOGGER.error("Error Message");
		return new Greeting(counter.incrementAndGet(), String.format(TEMPLATE, name));
	}
}