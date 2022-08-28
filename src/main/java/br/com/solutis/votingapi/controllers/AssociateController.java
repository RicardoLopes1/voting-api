package br.com.solutis.votingapi.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.solutis.votingapi.dto.AssociateDTO;
import br.com.solutis.votingapi.mapper.AssociateMapper;
import br.com.solutis.votingapi.services.AssociateService;
import lombok.AllArgsConstructor;

import java.net.URI;

import javax.validation.Valid;

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
  private final AssociateMapper associateMapper;

  @GetMapping(value = {"", "/"})
  public ResponseEntity<Page<AssociateDTO>> findAll(Pageable pageable) {
    var pageAssociate = associateService.findAll(pageable);
    var pageAssociateDto = associateMapper.toPageDto(pageAssociate);
    return ResponseEntity.ok(pageAssociateDto);
  }

  @PostMapping(value = {"", "/"})
  public ResponseEntity<String> save(@RequestBody @Valid AssociateDTO associateDto) {
    var associate = associateMapper.toEntity(associateDto);
    var response = associateService.save(associate);
    return ResponseEntity.created(URI.create("/associates")).body(response);
  }
  
}
