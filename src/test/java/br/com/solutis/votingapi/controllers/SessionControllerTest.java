package br.com.solutis.votingapi.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import br.com.solutis.votingapi.common.VotingAPIResponseEntityObject;
import br.com.solutis.votingapi.entities.Schedule;
import br.com.solutis.votingapi.entities.Session;
import br.com.solutis.votingapi.repositories.ScheduleRepository;
import br.com.solutis.votingapi.repositories.SessionRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class SessionControllerTest {
  
  @Autowired
  private MockMvc postman;

  @Autowired
  private ScheduleRepository scheduleRepository;

  @Autowired
  private SessionRepository sessionRepository;

  @Test
  public void shouldReturnStatusCreatedAndASession() {
    try {
      URI uri = new URI("/sessions/start");
      String json = "{\"name\":\"session teste\",\"scheduleId\":2,\"time\": 5}";

      postman.perform(MockMvcRequestBuilders.post(uri)
        .contentType("application/json")
        .content(json))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.header().string("Location", uri.toString()))
        .getClass().isAssignableFrom(Session.class);

    } catch (URISyntaxException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }

  @Test
  public void shouldReturnStatusCreatedAndASessionWithDefaulEndTime() {
    try {
      URI uri = new URI("/sessions/start");
      String json = "{\"name\":\"session teste\",\"scheduleId\":1,\"time\":0}";

      postman.perform(MockMvcRequestBuilders.post(uri)
        .contentType("application/json")
        .content(json))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.header().string("Location", uri.toString()))
        .getClass().isAssignableFrom(Session.class);

    } catch (URISyntaxException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }

  @Test
  public void shouldReturnStatusBadRequestIfNameOfSessionIsBlank() {
    try {
      URI uri = new URI("/sessions/start");
      String json = "{\"name\":\"   \",\"scheduleId\":1,\"time\": 5}";

      postman.perform(MockMvcRequestBuilders.post(uri)
        .contentType("application/json")
        .content(json))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .getClass().isAssignableFrom(VotingAPIResponseEntityObject.class);

    } catch (URISyntaxException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }

  @Test
  public void shouldReturnStatusBadRequestIfNameOfSessionIsEmpty() {
    try {
      URI uri = new URI("/sessions/start");
      String json = "{\"name\":\"\",\"scheduleId\":1,\"time\": 5}";

      postman.perform(MockMvcRequestBuilders.post(uri)
        .contentType("application/json")
        .content(json))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .getClass().isAssignableFrom(VotingAPIResponseEntityObject.class);

    } catch (URISyntaxException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }

  @Test
  public void shouldReturnStatusBadRequestIfScheduleIdIsNull() {
    try {
      URI uri = new URI("/sessions/start");
      String json = "{\"name\":\"Name of session\",\"scheduleId\":null,\"time\": 5}";

      postman.perform(MockMvcRequestBuilders.post(uri)
        .contentType("application/json")
        .content(json))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .getClass().isAssignableFrom(VotingAPIResponseEntityObject.class);

    } catch (URISyntaxException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }

  @Test
  public void shouldReturnStatusBadRequestIfScheduleNotExistsInDatabase() {
    try {
      URI uri = new URI("/sessions/start");
      String json = "{\"name\":\"Name of session\",\"scheduleId\":100000,\"time\": 5}";

      postman.perform(MockMvcRequestBuilders.post(uri)
        .contentType("application/json")
        .content(json))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .getClass().isAssignableFrom(VotingAPIResponseEntityObject.class);

    } catch (URISyntaxException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }

  @Test
  public void shouldReturnStatusBadRequestIfSessionAlreadyExistsWithSchedule() {
    try {
      URI uri = new URI("/sessions/start");
      String json = "{\"name\":\"Name of session teste 02\",\"scheduleId\":1,\"time\": 2}";

      Session session = new Session();
      session.setName("Name of session");
      session.setActive(true);
      LocalDateTime now = LocalDateTime.now();
      session.setStartDate(now);
      session.setEndDate(now.plusMinutes(5));

      Schedule schedule = scheduleRepository.findById(1L).get();
      session.setSchedule(schedule);

      sessionRepository.save(session);
      
      postman.perform(MockMvcRequestBuilders.post(uri)
        .contentType("application/json")
        .content(json))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .getClass().isAssignableFrom(VotingAPIResponseEntityObject.class);

    } catch (URISyntaxException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }

}
