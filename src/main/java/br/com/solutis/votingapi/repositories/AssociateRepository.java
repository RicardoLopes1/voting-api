package br.com.solutis.votingapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.solutis.votingapi.entities.Associate;

public interface AssociateRepository extends JpaRepository<Associate, Long> {

  List<Associate> findAll();
}
