package br.com.solutis.votingapi.services;

import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.solutis.votingapi.entities.Schedule;
import br.com.solutis.votingapi.exception.NotFoundException;
import br.com.solutis.votingapi.exception.ScheduleAlreadyExistsException;
import br.com.solutis.votingapi.repositories.ScheduleRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ScheduleService {
  
  private final ScheduleRepository scheduleRepository;

  @Transactional
  public Schedule save(Schedule schedule) {
    
    scheduleRepository.findByName(schedule.getName()).ifPresent(searchSchedule -> {
      throw new ScheduleAlreadyExistsException();
    });

    schedule.setCreatedDate(LocalDateTime.now());
    schedule = scheduleRepository.saveAndFlush(schedule);

    return schedule;
  }

  @Transactional(readOnly = true)
  public Schedule findById(Long scheduleId) {
    return scheduleRepository.findById(scheduleId)
      .orElseThrow(NotFoundException::new);
  }
  
}
