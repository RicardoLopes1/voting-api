package br.com.solutis.votingapi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.solutis.votingapi.entities.Schedule;
import br.com.solutis.votingapi.services.ScheduleService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/schedules")
public class ScheduleController {
  
  private final ScheduleService scheduleService;

  @PostMapping(value = "")
  public ResponseEntity<Object> save(@RequestBody Schedule schedule) {
    return scheduleService.save(schedule);
  }

  @GetMapping(value = "/{scheduleId}")
  public ResponseEntity<Object> findById(@PathVariable Long scheduleId) {
    return scheduleService.findById(scheduleId);
  }
  
}
