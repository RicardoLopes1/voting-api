package br.com.solutis.votingapi.services;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.solutis.votingapi.common.VotingAPIResponseEntityObject;
import br.com.solutis.votingapi.dto.ScheduleDTOInput;
import br.com.solutis.votingapi.entities.Schedule;
import br.com.solutis.votingapi.repositories.ScheduleRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ScheduleService {
  
  private final ScheduleRepository scheduleRepository;

  public ResponseEntity<Object> save(ScheduleDTOInput scheduleDtoInput) {
    
    Optional<Schedule> scheduleRequested = scheduleRepository.findByName(scheduleDtoInput.getName());
    if (scheduleRequested.isPresent()) {
      return ResponseEntity.badRequest().body(
        VotingAPIResponseEntityObject.builder()
          .status(HttpStatus.BAD_REQUEST)
          .message("Schedule already exists")
          .object(scheduleRequested.get())
          .build()
        );
    }

    Schedule schedule = new Schedule();
    schedule.setName(scheduleDtoInput.getName());
    schedule.setDescription(scheduleDtoInput.getDescription());
    schedule.setCreatorId(scheduleDtoInput.getCreatorId());
    schedule.setCreatedBy(scheduleDtoInput.getCreatedBy());
    schedule.setCreatedDate(LocalDateTime.now());
    schedule = scheduleRepository.saveAndFlush(schedule);

    return ResponseEntity.created(URI.create("/schedules")).body(schedule);
  }

  @Transactional(readOnly = true)
  public ResponseEntity<Object> findById(Long scheduleId) {
    
    Optional<Schedule> schedule = scheduleRepository.findById(scheduleId);

    if(schedule.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    
    return ResponseEntity.ok(schedule.get());
  }
  
}
