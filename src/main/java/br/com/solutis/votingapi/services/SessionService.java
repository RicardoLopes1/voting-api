package br.com.solutis.votingapi.services;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.solutis.votingapi.entities.Session;
import br.com.solutis.votingapi.repositories.SessionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Service
public class SessionService {
  
  private final SessionRepository sessionRepository;
  
  @Transactional
  public Session startSession(Session session, int timeOut) throws InterruptedException {

    session.setActive(true);
    LocalDateTime now = LocalDateTime.now();
    session.setStartDate(now);
    session.setEndDate(now.plusMinutes(timeOut));
    
    session = sessionRepository.saveAndFlush(session);
    
    this.disableSession(session.getId(), timeOut);
    
    return session;
  }

  @Async
  @Transactional
  public void disableSession(Long sessionId, int timeOut) throws InterruptedException {
    
    int oneMinute = 60 * 1000;
    int timeOutInMinutes = oneMinute * timeOut;
    Thread.sleep(timeOutInMinutes);
    
    sessionRepository.disableSession(sessionId);
    log.info("Session " + sessionId + " no active.");
  }

  @Transactional(readOnly = true)
  public Page<Session> findAll(Pageable pageable) {
    return sessionRepository.findAll(pageable);
  }
}
