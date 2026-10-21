package com.example.demo.controller;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import com.example.demo.dto.request.PersonRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PersonControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void fullCrudFlow() throws Exception {
		PersonRequest request = PersonRequest.builder()
			.firstName("John")
			.lastName("Doe")
			.email("john.doe@example.com")
			.dateOfBirth(LocalDate.of(1990, 1, 15))
			.isActive(true)
			.build();

		MvcResult result = mockMvc.perform(post("/api/persons")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.personId").exists())
			.andExpect(jsonPath("$.firstName").value("John"))
			.andExpect(jsonPath("$.lastName").value("Doe"))
			.andExpect(jsonPath("$.email").value("john.doe@example.com"))
			.andExpect(jsonPath("$.isActive").value(true))
			.andReturn();

		JsonNode response = objectMapper.readTree(result.getResponse().getContentAsString());
		int personId = response.get("personId").asInt();

		mockMvc.perform(get("/api/persons/{personId}", personId))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.email").value("john.doe@example.com"));

		mockMvc.perform(get("/api/persons"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].personId").value(personId));

		PersonRequest update = PersonRequest.builder()
			.firstName("Jane")
			.lastName("Doe")
			.email("jane.doe@example.com")
			.build();

		mockMvc.perform(put("/api/persons/{personId}", personId)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(update)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.firstName").value("Jane"))
			.andExpect(jsonPath("$.email").value("jane.doe@example.com"));

		mockMvc.perform(delete("/api/persons/{personId}", personId))
			.andExpect(status().isNoContent());

		mockMvc.perform(get("/api/persons/{personId}", personId))
			.andExpect(status().isNotFound());
	}

	@Test
	void createWithInvalidBodyReturnsBadRequest() throws Exception {
		PersonRequest request = PersonRequest.builder()
			.firstName("")
			.lastName("Doe")
			.email("not-an-email")
			.build();

		mockMvc.perform(post("/api/persons")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isBadRequest());
	}

	@Test
	void createWithDuplicateEmailReturnsConflict() throws Exception {
		PersonRequest request = PersonRequest.builder()
			.firstName("Alice")
			.lastName("Smith")
			.email("alice@example.com")
			.build();
		String body = objectMapper.writeValueAsString(request);

		mockMvc.perform(post("/api/persons")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body))
			.andExpect(status().isCreated());

		mockMvc.perform(post("/api/persons")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body))
			.andExpect(status().isConflict());
	}

}
