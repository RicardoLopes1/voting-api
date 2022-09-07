package br.com.solutis.votingapi.services;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.solutis.votingapi.common.VotingAPIResponseEntityObject;
import br.com.solutis.votingapi.dto.VotesCountingPerSessionDTO;
import br.com.solutis.votingapi.dto.VotingDTOInput;
import br.com.solutis.votingapi.entities.Session;
import br.com.solutis.votingapi.entities.Voting;
import br.com.solutis.votingapi.repositories.SessionRepository;
import br.com.solutis.votingapi.repositories.VotingRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class VotingService {
  
  private final SessionRepository sessionRepository;
  private final VotingRepository votingRepository;

  @Transactional(readOnly = false)
  public ResponseEntity<Object> save(VotingDTOInput votingDtoInput) {
    
    Optional<Session> session = sessionRepository.findById(votingDtoInput.getSessionId());
    if(session.isEmpty() || !session.get().isActive()) {
      return ResponseEntity.badRequest().body(
        VotingAPIResponseEntityObject.builder()
          .status(HttpStatus.BAD_REQUEST)
          .message("Session not active.")
          .object(session.orElse(null))
          .build()
        );
    }

    Optional<Voting> votingExist = votingRepository.findBySessionIdAndAssociateId(votingDtoInput.getSessionId(), votingDtoInput.getAssociateId());
    Voting newVoting = new Voting();

    if(votingExist.isPresent()) {
      newVoting = votingExist.get();
      newVoting.setVote(votingDtoInput.getVote().toLowerCase());
      newVoting.setVoteDate(LocalDateTime.now());
      newVoting = votingRepository.saveAndFlush(newVoting);
      return ResponseEntity.ok(newVoting);
    }
    
    newVoting.setSessionId(votingDtoInput.getSessionId());
    newVoting.setScheduleId(votingDtoInput.getScheduleId());
    newVoting.setAssociateId(votingDtoInput.getAssociateId());
    newVoting.setVote(votingDtoInput.getVote().toLowerCase());
    newVoting.setVoteDate(LocalDateTime.now());
    newVoting = votingRepository.saveAndFlush(newVoting);

    return ResponseEntity.created(URI.create("/votes")).body(newVoting);
  }

  @Transactional(readOnly = true)
  public ResponseEntity<Object> allVotesBySessionId(Long sessionId) {
    
    Optional<Session> session = sessionRepository.findById(sessionId);
    if(session.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    List<Voting> votings = votingRepository.findBySessionId(sessionId);
    double totalVotes = votings.size();
    double yesVotes = votings.stream()
      .filter(v -> v.getVote().equals("sim"))
      .count();
    double noVotes = totalVotes - yesVotes;

    return ResponseEntity.ok(
      VotesCountingPerSessionDTO.builder()
        .sessionId(votings.get(0).getSessionId())
        .votesYes(yesVotes)
        .percentageYes(yesVotes * 100 / totalVotes)
        .votesNo(noVotes)
        .percentageNo(noVotes * 100 / totalVotes)
        .scheduleWinner(yesVotes > noVotes)
        .schedule(session.get().getSchedule())
        .build()
      );
  }

}
