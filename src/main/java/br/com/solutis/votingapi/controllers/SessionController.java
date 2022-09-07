package br.com.solutis.votingapi.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.solutis.votingapi.dto.SessionDTOInput;
import br.com.solutis.votingapi.dto.SessionDTOOutput;
import br.com.solutis.votingapi.exception.NotFoundException;
import br.com.solutis.votingapi.exception.SessionAlreadyExistsException;
import br.com.solutis.votingapi.mapper.SessionMapper;
import br.com.solutis.votingapi.repositories.ScheduleRepository;
import br.com.solutis.votingapi.repositories.SessionRepository;
import br.com.solutis.votingapi.services.SessionService;
import lombok.AllArgsConstructor;

import java.net.URI;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@AllArgsConstructor
@RestController
@RequestMapping("/sessions")
public class SessionController {
  
  private final SessionService sessionService;
  private final SessionMapper sessionMapper;
  private final SessionRepository sessionRepository;
  private final ScheduleRepository scheduleRepository;

  @PostMapping(value = "/start")
  public ResponseEntity<SessionDTOOutput> startSession(@RequestBody @Valid SessionDTOInput sessionDtoInput) throws InterruptedException {
    
    sessionRepository.findByScheduleId(sessionDtoInput.getScheduleId()).ifPresent(searchSession -> {
      throw new SessionAlreadyExistsException();
    });

    var schedule = scheduleRepository.findById(sessionDtoInput.getScheduleId())
      .orElseThrow(NotFoundException::new);

    var session = sessionMapper.toEntity(sessionDtoInput);
    session.setSchedule(schedule);

    int timeOut = sessionDtoInput.getTime() > 1 ? 
      sessionDtoInput.getTime() : 
      sessionDtoInput.getDefaultTime();
  
    var newSession = sessionService.startSession(session, timeOut);
    var sessionDtoOutput = sessionMapper.toDTOOutput(newSession);
    
    return ResponseEntity.created(URI.create("/sessions/start")).body(sessionDtoOutput);
  }

  @GetMapping(value = {"/", ""})
  public ResponseEntity<Page<SessionDTOOutput>> findAll(Pageable pageable) {
    
    var sessions = sessionService.findAll(pageable);
    var sessionPageDtoOutput = sessionMapper.toPageDTOOutput(sessions);
    
    return ResponseEntity.ok(sessionPageDtoOutput);
  }
  
}
