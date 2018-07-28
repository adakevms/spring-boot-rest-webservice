package org.kevin.spring.boot.rest.webservice;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@SpringBootConfiguration
@Profile("it")
public class ITConfiguration {

	@Autowired
	private Environment environment;
	
	@Bean
	public URI webServiceFetchURI() {
		UriComponentsBuilder builder = baseURIBuilder();
		builder.path(environment.getProperty("webservice.fetchEndpoint"));
		UriComponents uriComponents = builder.build();
		return uriComponents.toUri();
	}

	@Bean
	public URI webServiceUpdateURI() {
		UriComponentsBuilder builder = baseURIBuilder();
		builder.path(environment.getProperty("webservice.updateEndpoint"));
		UriComponents uriComponents = builder.build();
		return uriComponents.toUri();
	}
	
	private UriComponentsBuilder baseURIBuilder() {
		UriComponentsBuilder builder = UriComponentsBuilder.newInstance();
		return builder
				.scheme(environment.getProperty("webservice.scheme"))
				.host(environment.getProperty("webservice.host"))
				.port(environment.getProperty("webservice.port"));
	}

}
