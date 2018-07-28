package org.kevin.spring.boot.rest.webservice.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import java.net.URL;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("it")
public class GreetingControllerTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(GreetingControllerTest.class);
	
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void noParamGreetingShouldReturnDefaultMessage() throws Exception {

		this.mockMvc.perform(get("/greeting")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.content").value("Hello, World!"));
	}

	@Test
	public void paramGreetingShouldReturnTailoredMessage() throws Exception {

		this.mockMvc.perform(get("/greeting").param("name", "Spring Community")).andDo(print())
				.andExpect(status().isOk()).andExpect(jsonPath("$.content").value("Hello, Spring Community!"));
	}
	
	@Test
	public void springUriComponentsBuilder() {
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
		UriComponents uriComponents = uriComponentsBuilder.scheme("http").host("www.example.com").build();
		LOGGER.info(uriComponents.toUriString());
		uriComponents = uriComponentsBuilder.scheme("http").host("www.example.com").port(8180).build();
		LOGGER.info(uriComponents.toUriString());
		URI uri = uriComponents.toUri();
		LOGGER.info(uri.toString());
	}
	
	@Test
	public void urlTest() throws Exception {
		URL url = new URL("http://www.byitself.com/something.html");
		LOGGER.info("url(String spec): {}", url.toString());

		url = new URL("http", "www.noport.com", "noPort.jsp");
		LOGGER.info("url(protocol, host, file): {}", url.toString());

		url = new URL("http", "web.kevin.org", 8080, "whatever.html");
		LOGGER.info("url(protocol, host, port, file): {}", url.toString());
	}

	@Test
	public void uriTest() throws Exception {
		URI uri = new URI("http", "www.urihost.com", "/", "abc");
		LOGGER.info("uri(scheme, host, path, fragment): {}", uri.toString());


	}

}