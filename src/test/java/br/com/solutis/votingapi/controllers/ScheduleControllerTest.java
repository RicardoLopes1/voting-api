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
import br.com.solutis.votingapi.entities.Schedule;

@SpringBootTest
@AutoConfigureMockMvc
public class ScheduleControllerTest {
  
  @Autowired
  private MockMvc postman;

  @Test
  public void shouldReturnStatusCreatedWhenSendASchedule() {
    try {
      URI uri = new URI("/schedules");
      String json = "{\"name\":\"schedule 100\",\"description\":\"schedule 100 description\",\"creatorId\": 1, \"createdBy\":\"Creator 100\"}";

      postman.perform(MockMvcRequestBuilders.post(uri)
        .contentType("application/json")
        .content(json))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.header().string("Location", uri.toString()))
        .getClass().isAssignableFrom(Schedule.class);

    } catch (URISyntaxException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }

  @Test
  public void shouldReturnStatusBadRequestIfScheduleAlreadyExists() {
    try {
      URI uri = new URI("/schedules");
      String json = "{\"name\":\"schedule 001\",\"description\":\"schedule 100 description\", \"creatorId\": 1, \"createdBy\":\"Creator 001\"}";

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
  public void shoudReturnScheduleById() {
    try {
      URI uri = new URI("/schedules/1");

      postman.perform(MockMvcRequestBuilders.get(uri))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .getClass().isAssignableFrom(Schedule.class);

    } catch (URISyntaxException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void shoudNotReturnScheduleByIdIfScheduleNotExist() {
    try {
      URI uri = new URI("/schedules/1000");

      postman.perform(MockMvcRequestBuilders.get(uri))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
        
    } catch (URISyntaxException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
