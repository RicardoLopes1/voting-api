package br.com.solutis.votingapi.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import br.com.solutis.votingapi.entities.Session;

public interface SessionRepository extends JpaRepository<Session, Long> {

  @Query("SELECT s FROM Session s WHERE s.active = true AND s.schedule.id = ?1")
  Optional<Session> findByScheduleId(Long scheduleId);

  @Modifying
  @Query(value = "UPDATE Session s SET s.active = false WHERE s.id = ?1",
      nativeQuery = false) 
  void disableSession(Long sessionId);

}
