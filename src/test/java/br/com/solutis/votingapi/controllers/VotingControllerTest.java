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
import br.com.solutis.votingapi.dto.VotesCountingPerSessionDTO;
import br.com.solutis.votingapi.entities.Voting;

@SpringBootTest
@AutoConfigureMockMvc
public class VotingControllerTest {
  
  @Autowired
  private MockMvc postman;

  @Test
  public void shouldReturnStatusCreatedWhenReceiveAVote() {
    try {
      URI uri = new URI("/votes");
      String json = "{\"sessionId\":2,\"scheduleId\":2,\"associateId\":2,\"vote\":\"sim\"}";

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
      String json = "{\"sessionId\":5,\"scheduleId\":5,\"associateId\":1,\"vote\":\"sim\"}";

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
      String json = "{\"sessionId\":1,\"scheduleId\":1,\"associateId\":1,\"vote\":\"nao\"}";

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
