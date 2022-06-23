package br.com.solutis.votingapi.repositories;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.util.Assert;

import br.com.solutis.votingapi.entities.Associate;

@DataJpaTest
public class AssociateRepositoryTest {
  
  @Autowired
  private AssociateRepository associateRepository;

  @Test
  public void shouldReturnAListOfAssociates() {
    List<Associate> associates = associateRepository.findAll();
    Assert.isTrue(associates.size() > 0, "Should return a list of associates");
  }

  @Test
  public void shouldReturnAnAssociateByCpf() {
    Optional<Associate> associate = associateRepository.findByCpf("12345687901");
    Assert.isTrue(associate.isPresent(), "Should return an associate by cpf");
  }

  @Test
  public void shouldNotReturnAnAssociateByCpf() {
    Optional<Associate> associate = associateRepository.findByCpf("");
    Assert.isTrue(associate.isEmpty(), "Should not return an associate by cpf");
  }
  
}
