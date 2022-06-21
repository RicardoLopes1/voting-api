package br.com.solutis.votingapi.services;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.solutis.votingapi.common.DisableSession;
import br.com.solutis.votingapi.common.VotingAPIResponseEntityObject;
import br.com.solutis.votingapi.dto.SessionDTOInput;
import br.com.solutis.votingapi.entities.Schedule;
import br.com.solutis.votingapi.entities.Session;
import br.com.solutis.votingapi.repositories.ScheduleRepository;
import br.com.solutis.votingapi.repositories.SessionRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class SessionService {
  
  private final SessionRepository sessionRepository;
  private final ScheduleRepository scheduleRepository;
  private final DisableSession disableSession;

  @Transactional(readOnly = false)
  public ResponseEntity<Object> startSession(SessionDTOInput sessionDtoIput){
    
    if(sessionDtoIput.getName().isBlank() || sessionDtoIput.getName().isEmpty()) {
      return ResponseEntity.badRequest().body(
        VotingAPIResponseEntityObject.builder()
          .status(HttpStatus.BAD_REQUEST)
          .message("Name is required.")
          .object(sessionDtoIput)
          .build()
        );
    }

    if(sessionDtoIput.getScheduleId() == null) {
      return ResponseEntity.badRequest().body(
        VotingAPIResponseEntityObject.builder()
          .status(HttpStatus.BAD_REQUEST)
          .message("ID Schedule is required.")
          .object(sessionDtoIput)
          .build()
        );
    }

    Optional<Schedule> schedule = scheduleRepository.findById(sessionDtoIput.getScheduleId());
    if(schedule.isEmpty()){
      return ResponseEntity.badRequest().body(
        VotingAPIResponseEntityObject.builder()
          .status(HttpStatus.NOT_FOUND)
          .message("ID Schedule not found.")
          .object(sessionDtoIput)
          .build()
        );
    }

    Optional<Session> sessionOnDb = sessionRepository.findByScheduleId(schedule.get().getId());
    if(sessionOnDb.isPresent()) {
      return ResponseEntity.badRequest().body(
        VotingAPIResponseEntityObject.builder()
          .status(HttpStatus.BAD_REQUEST)
          .message("Session with Schedule ID " + schedule.get().getId() + " already exists.")
          .object(sessionOnDb.get())
          .build()
        );
    }

    Session session = new Session();
    session.setName(sessionDtoIput.getName());
    session.setActive(true);
    LocalDateTime now = LocalDateTime.now();
    session.setStartDate(now);
    
    int incrementMinutes = sessionDtoIput.getTime() > 0 ? 
      sessionDtoIput.getTime() : 
      sessionDtoIput.getDefaultTime();

    session.setEndDate(now.plusMinutes(incrementMinutes));
    session.setSchedule(schedule.get());
    
    session = sessionRepository.saveAndFlush(session);
    
    disableSession.disable(session.getId(), incrementMinutes);
    
    return ResponseEntity.created(URI.create("/sessions/start")).body(session);
  }
}
