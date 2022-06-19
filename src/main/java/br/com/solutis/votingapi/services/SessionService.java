package br.com.solutis.votingapi.services;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

  public ResponseEntity<Session> startSession(SessionDTOInput sessionDtoIput){
    
    if(sessionDtoIput.getName().isBlank() || sessionDtoIput.getName().isEmpty()){
      return ResponseEntity.badRequest().body(null);
    }

    if(sessionDtoIput.getScheduleId() == null){
      return ResponseEntity.badRequest().body(null);
    }

    Optional<Schedule> schedule = scheduleRepository.findById(sessionDtoIput.getScheduleId());
    if(schedule.isEmpty()){
      return ResponseEntity.badRequest().body(null);
    }

    if(sessionRepository.findByScheduleId(schedule.get().getId()).isPresent()) {
      return ResponseEntity.badRequest().body(null);
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
