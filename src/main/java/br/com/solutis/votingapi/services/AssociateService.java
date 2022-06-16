package br.com.solutis.votingapi.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.solutis.votingapi.assembler.AssociateAssembler;
import br.com.solutis.votingapi.dto.AssociateDTO;
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
}
