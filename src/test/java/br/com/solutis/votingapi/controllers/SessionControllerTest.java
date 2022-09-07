package br.com.solutis.votingapi.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.solutis.votingapi.dto.SessionDTOInput;
import br.com.solutis.votingapi.dto.SessionDTOOutput;

@SpringBootTest
@AutoConfigureMockMvc
class SessionControllerTest {

  private SessionDTOInput sessionDTOInput;

  @Autowired
  private MockMvc postman;

  @Autowired
  private ObjectMapper objectMapper;

  @BeforeEach
  public void setUp() {
    sessionDTOInput = new SessionDTOInput("session test 6", 6L, 1, 6);      
  }

  @Test
  void shouldReturnStatusCreatedAndASession() throws Exception {
    // Arrange
    String uri = "/sessions/start";
    String json = objectMapper.writeValueAsString(sessionDTOInput);

    // Assert
    postman.perform(MockMvcRequestBuilders.post(uri)
        .contentType("application/json")
        .content(json))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.header().string("Location", uri))
        .getClass().isAssignableFrom(SessionDTOOutput.class);
  }

  @Test
  void shouldReturnStatusCreatedAndASessionWithDefaulEndTime() throws Exception {
    // Arrange
    String uri = "/sessions/start";
    sessionDTOInput.setScheduleId(7L);
    sessionDTOInput.setTime(0);
    String json = objectMapper.writeValueAsString(sessionDTOInput);

    // Assert
    postman.perform(MockMvcRequestBuilders.post(uri)
        .contentType("application/json")
        .content(json))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.header().string("Location", uri))
        .getClass().isAssignableFrom(SessionDTOOutput.class);
  }

  @Test
  void shouldReturnStatusBadRequestIfNameOfSessionIsBlank() throws Exception {
    // Arrange
    String uri = "/sessions/start";
    sessionDTOInput.setName("  ");
    String json = objectMapper.writeValueAsString(sessionDTOInput);

    // Assert
    postman.perform(MockMvcRequestBuilders.post(uri)
        .contentType("application/json")
        .content(json))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  void shouldReturnStatusBadRequestIfNameOfSessionIsEmpty() throws Exception {
    // Arrange
    String uri = "/sessions/start";
    sessionDTOInput.setName("");
    String json = objectMapper.writeValueAsString(sessionDTOInput);

    // Assert
    postman.perform(MockMvcRequestBuilders.post(uri)
        .contentType("application/json")
        .content(json))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  void shouldReturnStatusBadRequestIfScheduleIdIsNull() throws Exception {
    // Arrange
    String uri = "/sessions/start";
    sessionDTOInput.setScheduleId(null);
    String json = objectMapper.writeValueAsString(sessionDTOInput);

    postman.perform(MockMvcRequestBuilders.post(uri)
        .contentType("application/json")
        .content(json))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  void shouldReturnStatusNotFoundIfScheduleNotExistsInDatabase() throws Exception {
    // Arrange
    String uri = "/sessions/start";
    sessionDTOInput.setScheduleId(9999L);
    String json = objectMapper.writeValueAsString(sessionDTOInput);

    // Assert
    postman.perform(MockMvcRequestBuilders.post(uri)
        .contentType("application/json")
        .content(json))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  void shouldReturnStatusBadRequestIfSessionAlreadyExistsWithSchedule() throws Exception {
    // Arrange
    String uri = "/sessions/start";
    sessionDTOInput.setScheduleId(1L);
    String json = objectMapper.writeValueAsString(sessionDTOInput);

    // Assert
    postman.perform(MockMvcRequestBuilders.post(uri)
        .contentType("application/json")
        .content(json))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

}
