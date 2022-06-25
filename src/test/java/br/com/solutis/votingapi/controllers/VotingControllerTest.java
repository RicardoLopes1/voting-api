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
import br.com.solutis.votingapi.dto.VotesCountingPerSessionDTO;
import br.com.solutis.votingapi.entities.Schedule;
import br.com.solutis.votingapi.entities.Session;
import br.com.solutis.votingapi.entities.Voting;
import br.com.solutis.votingapi.repositories.ScheduleRepository;
import br.com.solutis.votingapi.repositories.SessionRepository;
import br.com.solutis.votingapi.repositories.VotingRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class VotingControllerTest {
  
  @Autowired
  private MockMvc postman;

  @Autowired
  private SessionRepository sessionRepository;

  @Autowired
  private ScheduleRepository scheduleRepository;

  @Autowired
  private VotingRepository votingRepository;

  @Test
  public void shouldReturnStatusCreatedWhenReceiveAVote() {
    try {
      URI uri = new URI("/votes");
      
      Session session = new Session();
      session.setName("Name of session 03");
      session.setActive(true);
      LocalDateTime now = LocalDateTime.now();
      session.setStartDate(now);
      session.setEndDate(now.plusMinutes(5));

      Schedule schedule = scheduleRepository.findById(3L).get();
      session.setSchedule(schedule);

      session = sessionRepository.saveAndFlush(session);
      String json = "{\"sessionId\":"+ session.getId() +",\"scheduleId\":3,\"associateId\":1,\"vote\":\"sim\"}";

      postman.perform(MockMvcRequestBuilders.put(uri)
        .contentType("application/json")
        .content(json))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.header().string("Location", uri.toString()))
        .getClass().isAssignableFrom(VotesCountingPerSessionDTO.class);

    } catch (URISyntaxException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void shouldReturnStatusBadResquestIfSessionNotExistInDatabase() {
    try {
      URI uri = new URI("/votes");
      String json = "{\"sessionId\":100000,\"scheduleId\":1,\"associateId\":1,\"vote\":\"sim\"}";

      postman.perform(MockMvcRequestBuilders.put(uri)
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
  public void shouldReturnStatusBadResquestIfSessionIsNotActive() {
    try {
      URI uri = new URI("/votes");
      
      Session session = new Session();
      session.setName("Name of session 04");
      session.setActive(false);
      LocalDateTime now = LocalDateTime.now();
      session.setStartDate(now);
      session.setEndDate(now.plusMinutes(5));

      Schedule schedule = scheduleRepository.findById(4L).get();
      session.setSchedule(schedule);

      session = sessionRepository.saveAndFlush(session);
      String json = "{\"sessionId\":"+ session.getId() +",\"scheduleId\":4,\"associateId\":1,\"vote\":\"sim\"}";

      postman.perform(MockMvcRequestBuilders.put(uri)
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
  public void shouldReturnStatusOKIfVotingAlreadyExists() {
    try {
      URI uri = new URI("/votes");

      Session session = new Session();
      session.setName("Name of session 05");
      session.setActive(true);
      LocalDateTime now = LocalDateTime.now();
      session.setStartDate(now);
      session.setEndDate(now.plusMinutes(5));

      Schedule schedule = scheduleRepository.findById(5L).get();
      session.setSchedule(schedule);
      
      Voting newVote = new Voting();
      newVote.setSessionId(session.getId());
      newVote.setScheduleId(session.getSchedule().getId());
      newVote.setAssociateId(1L);
      newVote.setVote("sim");
      newVote.setVoteDate(LocalDateTime.now());

      newVote = votingRepository.saveAndFlush(newVote);
      
      String json = "{\"sessionId\":"+ newVote.getSessionId() +",\"scheduleId\":"+ newVote.getScheduleId() +",\"associateId\":"+ newVote.getAssociateId() +",\"vote\":\"sim\"}";

      postman.perform(MockMvcRequestBuilders.put(uri)
        .contentType("application/json")
        .content(json))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .getClass().isAssignableFrom(Voting.class);

    } catch (URISyntaxException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void shouldReturnAllVotesBySessionId() {
    try {
      URI uri = new URI("/votes/1");
      
      postman.perform(MockMvcRequestBuilders.get(uri)
        .contentType("application/json"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .getClass().isAssignableFrom(VotesCountingPerSessionDTO.class);
      
    } catch (URISyntaxException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void shouldReturnStatusNotFoundIfSessionNotExistsInDatabase() {
    try {
      URI uri = new URI("/votes/1000000");
      
      postman.perform(MockMvcRequestBuilders.get(uri)
        .contentType("application/json"))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
      
    } catch (URISyntaxException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
