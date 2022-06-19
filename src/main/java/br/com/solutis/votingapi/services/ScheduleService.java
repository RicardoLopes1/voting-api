package br.com.solutis.votingapi.services;

import java.net.URI;
import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.solutis.votingapi.entities.Schedule;
import br.com.solutis.votingapi.repositories.ScheduleRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ScheduleService {
  
  private final ScheduleRepository scheduleRepository;

  public ResponseEntity<Schedule> save(Schedule schedule) {
    
    if (scheduleRepository.findByName(schedule.getName()).isPresent()) {
      return ResponseEntity.badRequest().body(null);
    }

    schedule.setCreatedDate(LocalDateTime.now());
    schedule = scheduleRepository.saveAndFlush(schedule);

    return ResponseEntity.created(URI.create("/schedules")).body(schedule);
  }
}
