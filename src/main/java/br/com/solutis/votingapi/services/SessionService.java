package br.com.solutis.votingapi.services;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.solutis.votingapi.common.DisableSession;
import br.com.solutis.votingapi.entities.Session;
import br.com.solutis.votingapi.repositories.SessionRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class SessionService {
  
  private final SessionRepository sessionRepository;
  private final DisableSession disableSession;
  
  @Transactional
  public Session startSession(Session session, int timeOut) throws InterruptedException {

    session.setActive(true);
    LocalDateTime now = LocalDateTime.now();
    session.setStartDate(now);
    session.setEndDate(now.plusMinutes(timeOut));
    
    session = sessionRepository.saveAndFlush(session);
    
    disableSession.disable(session.getId(), timeOut);
    
    return session;
  }

  @Transactional(readOnly = true)
  public Page<Session> findAll(Pageable pageable) {
    return sessionRepository.findAll(pageable);
  }
}
