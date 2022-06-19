package br.com.solutis.votingapi.services;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.solutis.votingapi.common.VotingAPIResponseEntityObject;
import br.com.solutis.votingapi.entities.Schedule;
import br.com.solutis.votingapi.repositories.ScheduleRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ScheduleService {
  
  private final ScheduleRepository scheduleRepository;

  public ResponseEntity<Object> save(Schedule schedule) {
    
    Optional<Schedule> scheduleRequested = scheduleRepository.findByName(schedule.getName());
    if (scheduleRequested.isPresent()) {
      return ResponseEntity.badRequest().body(
        VotingAPIResponseEntityObject.builder()
          .status(HttpStatus.BAD_REQUEST)
          .message("Schedule already exists")
          .object(scheduleRequested.get())
          .build()
        );
    }

    schedule.setCreatedDate(LocalDateTime.now());
    schedule = scheduleRepository.saveAndFlush(schedule);

    return ResponseEntity.created(URI.create("/schedules")).body(schedule);
  }
}
