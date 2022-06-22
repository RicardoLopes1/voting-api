package br.com.solutis.votingapi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.solutis.votingapi.dto.VotingDTOInput;
import br.com.solutis.votingapi.services.VotingService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/votes")
public class VotingController {
  
  private final VotingService votingService;

  @PutMapping(value = "")
  public ResponseEntity<Object> save(@RequestBody VotingDTOInput votingDtoInput) {
    return votingService.save(votingDtoInput);
  }

  @GetMapping(value = "/{sessionId}")
  public ResponseEntity<Object> allVotesBySessionId(@PathVariable Long sessionId) {
    return votingService.allVotesBySessionId(sessionId);
  }

}
