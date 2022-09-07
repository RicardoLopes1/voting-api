package br.com.solutis.votingapi.common;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.solutis.votingapi.repositories.SessionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class DisableSession {
  
  private final SessionRepository sessionRepository;

  @Async
  @Transactional
  public void disable(Long sessionId, int timeOut) throws InterruptedException {
    
    int oneMinute = 60 * 1000;
    int timeOutInMinutes = oneMinute * timeOut;
    Thread.sleep(timeOutInMinutes);
    
    sessionRepository.disableSession(sessionId);
    log.info("Session " + sessionId + " no active.");
  }

}
