package br.com.solutis.votingapi.controllers;

import java.net.URI;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.solutis.votingapi.dto.ScheduleDTOInput;
import br.com.solutis.votingapi.dto.ScheduleDTOOutput;
import br.com.solutis.votingapi.mapper.ScheduleMapper;
import br.com.solutis.votingapi.services.ScheduleService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/schedules")
public class ScheduleController {
  
  private final ScheduleService scheduleService;
  private final ScheduleMapper scheduleMapper;

  @PostMapping(value = {"", "/"})
  public ResponseEntity<ScheduleDTOOutput> save(@RequestBody @Valid ScheduleDTOInput scheduleDtoInput) {
    var schedule = scheduleMapper.toEntity(scheduleDtoInput);
    var newSchedule = scheduleService.save(schedule);
    var scheduleDtoOutput = scheduleMapper.toDTOOutput(newSchedule);
    return ResponseEntity.created(URI.create("/schedules")).body(scheduleDtoOutput);
  }

  @GetMapping(value = "/{scheduleId}")
  public ResponseEntity<ScheduleDTOOutput> findById(@PathVariable Long scheduleId) {
    var schedule = scheduleService.findById(scheduleId);
    var scheduleDtoOutput = scheduleMapper.toDTOOutput(schedule);
    return ResponseEntity.ok(scheduleDtoOutput);
  }
  
}
