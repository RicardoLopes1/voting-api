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

import br.com.solutis.votingapi.dto.ScheduleDTOInput;
import br.com.solutis.votingapi.dto.ScheduleDTOOutput;
import br.com.solutis.votingapi.entities.Schedule;

@SpringBootTest
@AutoConfigureMockMvc
class ScheduleControllerTest {

  private ScheduleDTOInput scheduleDTOInput;

  @Autowired
  private MockMvc postman;

  @Autowired
  private ObjectMapper objectMapper;

  @BeforeEach
  public void setUp() {
    scheduleDTOInput = ScheduleDTOInput.builder()
        .name("Schedule 100")
        .description("Schedule 100 description")
        .creatorId(1L)
        .createdBy("Creator 100")
        .build();
  }

  @Test
  void shouldReturnStatusCreatedWhenSendASchedule() throws Exception {
    // Arrange
    String uri = "/schedules";
    String json = objectMapper.writeValueAsString(scheduleDTOInput);

    // Assert
    postman.perform(MockMvcRequestBuilders.post(uri)
        .contentType("application/json")
        .content(json))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.header().string("Location", uri))
        .getClass().isAssignableFrom(Schedule.class);
  }

  @Test
  void shouldReturnStatusBadRequestIfScheduleAlreadyExists() throws Exception {
    // Arrange
    String uri = "/schedules";
    scheduleDTOInput.setName("schedule 001");
    String json = objectMapper.writeValueAsString(scheduleDTOInput);

    // Assert
    postman.perform(MockMvcRequestBuilders.post(uri)
        .contentType("application/json")
        .content(json))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  void shoudReturnScheduleById() throws Exception {
    // Arrange
    String uri = "/schedules/1";

    // Assert
    postman.perform(MockMvcRequestBuilders.get(uri))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .getClass().isAssignableFrom(ScheduleDTOOutput.class);
  }

  @Test
  void shoudNotReturnScheduleByIdIfScheduleNotExist() throws Exception {
    // Arrange
    String uri = "/schedules/1000";

    // Assert
    postman.perform(MockMvcRequestBuilders.get(uri))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

}
