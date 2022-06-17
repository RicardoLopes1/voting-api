package br.com.solutis.votingapi.services;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.solutis.votingapi.assembler.AssociateAssembler;
import br.com.solutis.votingapi.dto.AssociateDTO;
import br.com.solutis.votingapi.entities.Associate;
import br.com.solutis.votingapi.repositories.AssociateRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AssociateService {
  
  private final AssociateRepository associateRepository;
  private final AssociateAssembler associateAssembler;

  public Page<AssociateDTO> findAll(Pageable pageable) {
    Page<AssociateDTO> associateDtos = associateAssembler.toPageDto(associateRepository.findAll(pageable));
             
    return associateDtos;
  }

  public ResponseEntity<String> save(AssociateDTO associateDto) {
    
    if(cpfIsNotValid(associateDto.getCpf())) {
      return ResponseEntity.badRequest().body("CPF is not valid.");
    }

    if(associateRepository.findByCpf(associateDto.getCpf()).isPresent()){
      return ResponseEntity.badRequest().body("CPF already exists.");
    }

    Associate associate = associateAssembler.toEntity(associateDto);
    associate = associateRepository.saveAndFlush(associate);
    return ResponseEntity.created(URI.create("/associates")).body("Associate created: " + associate.getName());
  }

  public boolean cpfIsValid(String cpf) {
    return cpf.length() == 11;
  }

  public boolean cpfIsNotValid(String cpf) {
    return !this.cpfIsValid(cpf);
  }
}
