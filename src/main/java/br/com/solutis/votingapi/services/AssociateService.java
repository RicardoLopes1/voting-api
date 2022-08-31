package br.com.solutis.votingapi.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.solutis.votingapi.entities.Associate;
import br.com.solutis.votingapi.exception.CPFAlreadyExistsException;
import br.com.solutis.votingapi.repositories.AssociateRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AssociateService {
  
  private final AssociateRepository associateRepository;

  @Transactional(readOnly = true)
  public Page<Associate> findAll(Pageable pageable) {
    return associateRepository.findAll(pageable);
  }

  @Transactional
  public String save(Associate associate) {
    associateRepository.findByCpf(associate.getCpf()).ifPresent( existingAssociate -> {
      throw new CPFAlreadyExistsException();
    });

    associateRepository.saveAndFlush(associate);

    return "Associate created: " + associate.getName();
  }

}
