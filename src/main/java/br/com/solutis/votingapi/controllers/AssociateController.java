package br.com.solutis.votingapi.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.solutis.votingapi.dto.AssociateDTO;
import br.com.solutis.votingapi.services.AssociateService;
import lombok.AllArgsConstructor;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/associates")
public class AssociateController {
  
  private final AssociateService associateService;

  @GetMapping(value = "")
  public ResponseEntity<Page<AssociateDTO>> findAll(Pageable pageable) {
      return ResponseEntity.ok(associateService.findAll(pageable));
  }

  @PostMapping(value = "")
  public ResponseEntity<String> save(@RequestBody AssociateDTO associateDto) {
    return associateService.save(associateDto);
  }
  
}
