package br.com.solutis.votingapi.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.solutis.votingapi.dto.SessionDTOInput;
import br.com.solutis.votingapi.services.SessionService;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@AllArgsConstructor
@RestController
@RequestMapping("/sessions")
public class SessionController {
  
  private final SessionService sessionService;

  @PostMapping(value = "/start")
  public ResponseEntity<Object> startSession(@RequestBody SessionDTOInput sessionDtoIput) {
      return sessionService.startSession(sessionDtoIput);
  }
  
}
