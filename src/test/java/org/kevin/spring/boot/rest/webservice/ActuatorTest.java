package org.kevin.spring.boot.rest.webservice;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
@ActiveProfiles("it")
public class ActuatorTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(ActuatorTest.class);
	private static final String LOGGER_LEVEL_DEBUG_POST_STRING = "{\"configuredLevel\":\"DEBUG\"}";
	private static final String LOGGER_LEVEL_DEBUG_STRING = "{\"configuredLevel\":\"DEBUG\",\"effectiveLevel\":\"DEBUG\"}";
	private static final String LOGGER_LEVEL_ERROR_POST_STRING = "{\"configuredLevel\":\"ERROR\"}";
	private static final String LOGGER_LEVEL_ERROR_STRING = "{\"configuredLevel\":\"ERROR\",\"effectiveLevel\":\"ERROR\"}";

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void displayLoggers() throws Exception {
		MvcResult result = mockMvc.perform(get("/actuator/loggers")).andDo(print()).andExpect(status().isOk()).andReturn();
		LOGGER.info(result.getResponse().getContentAsString());
	}

	@Test
	public void changeLoggingLevelWithActuator() throws Exception {
		mockMvc.perform(get("/actuator/loggers/ROOT")).andDo(print()).andExpect(status().isOk());
		mockMvc.perform(post("/actuator/loggers/ROOT").contentType(MediaType.APPLICATION_JSON)
				.content(LOGGER_LEVEL_ERROR_POST_STRING)).andExpect(status().isNoContent());
		mockMvc.perform(get("/actuator/loggers/ROOT")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(LOGGER_LEVEL_ERROR_STRING));
		mockMvc.perform(post("/actuator/loggers/ROOT").contentType(MediaType.APPLICATION_JSON)
				.content(LOGGER_LEVEL_DEBUG_POST_STRING)).andExpect(status().isNoContent());
		mockMvc.perform(get("/actuator/loggers/ROOT")).andDo(print()).andExpect(status().isOk())
		.andExpect(content().string(LOGGER_LEVEL_DEBUG_STRING));
	}

	@Test
	public void displayActuator() throws Exception {
		mockMvc.perform(get("/actuator/info")).andDo(print());
	}

	@Test
	public void displayEnvironment() throws Exception {
		MvcResult result = mockMvc.perform(get("/actuator/env")).andDo(print()).andReturn();
		String jsonContentString = result.getResponse().getContentAsString();
		System.out.println(jsonContentString);
		LOGGER.info("contentString: {}", jsonContentString);
	}

	@Test
	public void displaySingleProperty() throws Exception {
		MvcResult result = mockMvc.perform(get("/actuator/env/my.property.value")).andDo(print()).andReturn();
		String jsonContentString = result.getResponse().getContentAsString();
		System.out.println(jsonContentString);
		LOGGER.info("contentString: {}", jsonContentString);
	}

}