package br.com.solutis.votingapi.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.solutis.votingapi.entities.Session;

public interface SessionRepository extends JpaRepository<Session, Long> {

  Optional<Session> findByScheduleId(Long scheduleId);

}
