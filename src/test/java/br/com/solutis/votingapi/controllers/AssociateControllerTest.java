package br.com.solutis.votingapi.controllers;

import org.h2.mvstore.Page;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.solutis.votingapi.dto.AssociateDTO;

@SpringBootTest
@AutoConfigureMockMvc
public class AssociateControllerTest {
  
  private AssociateDTO associateDto;
  private static String PATH_ASSOCIATES = "/associates";

  @Autowired
  private MockMvc postman;

  @Autowired
  private ObjectMapper mapper;

  @BeforeEach
  public void setUp() {
    associateDto = AssociateDTO.builder()
        .name("Ricardo")
        .cpf("521.532.120-57")
        .email("a@email.com.br")
        .build();      
  }

  @Test
  public void shouldReturnStatusOKForURIAssociates() throws Exception {
    // Arrange
    String uri = PATH_ASSOCIATES + "?page=0&size=12";
    
    // Assert
    postman.perform(MockMvcRequestBuilders.get(uri))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
      .getClass().isAssignableFrom(Page.class);
  }

  @Test
  public void shouldReturnTheCreatedAssociateAndStatusCreatedForURIAssociates() throws Exception {
    
    // Arrange
    String uri = PATH_ASSOCIATES;
    String json = mapper.writeValueAsString(associateDto);

    // Assert
    postman.perform(MockMvcRequestBuilders.post(uri)
      .content(json)
      .contentType("application/json"))
      .andExpect(MockMvcResultMatchers.status().isCreated())
      .andExpect(MockMvcResultMatchers.header().string("Location", uri))
      .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"));
  }

  @Test
  public void shouldReturnBadRequestForURIAssociatesIfCpfIsNotValid() throws Exception {
    
    // Arrange
    String uri = PATH_ASSOCIATES;
    associateDto.setCpf("123.456.789-11");
    String json = mapper.writeValueAsString(associateDto);

    // Assert
    postman.perform(MockMvcRequestBuilders.post(uri)
      .content(json)
      .contentType("application/json"))
      .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  public void shouldReturnBadRequestIfAssociateAlreadyExists() throws Exception {
    
    // Arrange
    AssociateDTO associateDtoExists = AssociateDTO.builder()
      .name("Ricardo")
      .cpf("123.456.879-01")
      .email("a@email.com.br")
      .build();

    String uri = PATH_ASSOCIATES;
    String json = mapper.writeValueAsString(associateDtoExists);

    // Assert
    postman.perform(MockMvcRequestBuilders.post(uri)
      .content(json)
      .contentType("application/json"))
      .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

}
