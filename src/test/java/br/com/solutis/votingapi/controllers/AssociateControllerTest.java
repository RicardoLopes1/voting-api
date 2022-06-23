package br.com.solutis.votingapi.controllers;

import java.net.URI;
import java.net.URISyntaxException;

import org.h2.mvstore.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
public class AssociateControllerTest {
  
  @Autowired
  private MockMvc postman;

  @Test
  public void shouldReturnStatusOKForURIAssociates() {
    try {
      URI uri = new URI("/associates?page=0&size=12");
      postman.perform(MockMvcRequestBuilders.get(uri))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .getClass().isAssignableFrom(Page.class);

    } catch (URISyntaxException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void shouldReturnTheCreatedAssociateAndStatusCreatedForURIAssociates() {
    try {
      URI uri = new URI("/associates");
      String json = "{\"name\":\"Teste\",\"cpf\":\"12345678999\",\"email\":\"emaildeteste@solutis.com.br\"}";

      postman.perform(MockMvcRequestBuilders.post(uri)
        .content(json)
        .contentType("application/json"))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"));

    } catch (URISyntaxException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void shouldReturnBadRequestForURIAssociatesIfCpfIsNotValid() {
    try {
      URI uri = new URI("/associates");
      String json = "{\"name\":\"Teste\",\"cpf\":\"12345678\",\"email\":\"emaildeteste@solutis.com.br\"}";

      postman.perform(MockMvcRequestBuilders.post(uri)
        .content(json)
        .contentType("application/json"))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"));
        
    } catch (URISyntaxException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void shouldReturnBadRequestIfAssociateAlreadyExist() {
    try {
      URI uri = new URI("/associates");
      String json = "{\"name\":\"Teste\",\"cpf\":\"12345687901\",\"email\":\"emaildeteste@solutis.com.br\"}";

      postman.perform(MockMvcRequestBuilders.post(uri)
        .content(json)
        .contentType("application/json"))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"));
        
    } catch (URISyntaxException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
