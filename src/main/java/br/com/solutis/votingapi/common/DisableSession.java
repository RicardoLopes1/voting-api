package br.com.solutis.votingapi.common;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.solutis.votingapi.repositories.SessionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class DisableSession {
  
  private final SessionRepository sessionRepository;

  @Async
  @Transactional(readOnly = false)
  public void disable(Long sessionId, int timeOut) {
    int oneMinute = 60 * 1000;
    try {
      Thread.sleep(oneMinute * timeOut);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    sessionRepository.disableSession(sessionId);
    log.info("Session " + sessionId + " no active.");
  }
  
}
