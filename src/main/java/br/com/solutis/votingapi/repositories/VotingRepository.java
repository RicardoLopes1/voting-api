package br.com.solutis.votingapi.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.solutis.votingapi.entities.Voting;

public interface VotingRepository extends JpaRepository<Voting, Long> {

  @Query("UPDATE Voting v SET v.vote = ?1, v.voteDate = ?2 WHERE v.sessionId = ?3 AND v.associateId = ?4")
  Voting saveVoting(String vote, LocalDateTime voteDate, Long sessionId, Long associateId);

  Optional<Voting> findBySessionIdAndAssociateId(Long sessionId, Long associateId);

  List<Voting> findBySessionId(Long sessionId);
}