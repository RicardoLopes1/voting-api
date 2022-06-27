package br.com.solutis.votingapi.controllers;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import br.com.solutis.votingapi.common.VotingAPIResponseEntityObject;
import br.com.solutis.votingapi.entities.Session;

@SpringBootTest
@AutoConfigureMockMvc
public class SessionControllerTest {
  
  @Autowired
  private MockMvc postman;

  @Test
  public void shouldReturnStatusCreatedAndASession() {
    try {
      URI uri = new URI("/sessions/start");
      String json = "{\"name\":\"session test 6\",\"scheduleId\":6,\"time\": 6}";

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
      String json = "{\"name\":\"session test 7\",\"scheduleId\":7,\"time\":0}";

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
      String json = "{\"name\":\"   \",\"scheduleId\":8,\"time\": 8}";

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
      String json = "{\"name\":\"\",\"scheduleId\":8,\"time\": 8}";

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
      String json = "{\"name\":\"Name of session 8\",\"scheduleId\":null,\"time\": 8}";

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
      String json = "{\"name\":\"Name of session teste 01\",\"scheduleId\":1,\"time\": 2}";
      
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
