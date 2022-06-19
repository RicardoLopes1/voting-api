package br.com.solutis.votingapi.services;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

    if(sessionDtoIput.getScheduleId() == null){
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
          .status(HttpStatus.BAD_REQUEST)
          .message("ID Schedule not found.")
          .object(sessionDtoIput)
          .build()
        );
    }

    if(sessionRepository.findByScheduleId(schedule.get().getId()).isPresent()) {
      return ResponseEntity.badRequest().body(
        VotingAPIResponseEntityObject.builder()
          .status(HttpStatus.BAD_REQUEST)
          .message("Session with Schedule ID " + schedule.get().getId() + " already exists.")
          .object(sessionDtoIput)
          .build()
        );
    }

    Session session = new Session();
    session.setName(sessionDtoIput.getName());
    session.setActive(true);
    LocalDateTime now = LocalDateTime.now();
    session.setStartDate(now);
    session.setEndDate(now.plusMinutes(
      sessionDtoIput.getTime() > 0 ? 
      sessionDtoIput.getTime() : 
      sessionDtoIput.getDefaultTime()));
    session.setSchedule(schedule.get());

    session = sessionRepository.saveAndFlush(session);
    
    return ResponseEntity.created(URI.create("/sessions/start")).body(session);
  }
}
