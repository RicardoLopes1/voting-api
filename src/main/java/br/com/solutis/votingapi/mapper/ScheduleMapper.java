package br.com.solutis.votingapi.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import br.com.solutis.votingapi.dto.ScheduleDTOInput;
import br.com.solutis.votingapi.dto.ScheduleDTOOutput;
import br.com.solutis.votingapi.entities.Schedule;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ScheduleMapper {
  
  private final ModelMapper mapper;

  public ScheduleDTOInput toTTOInput(Schedule schedule) {
    return mapper.map(schedule, ScheduleDTOInput.class);
  }

  public ScheduleDTOOutput toDTOOutput(Schedule schedule) {
    return mapper.map(schedule, ScheduleDTOOutput.class);
  }

  public Schedule toEntity(ScheduleDTOInput scheduleDtoInput) {
    return mapper.map(scheduleDtoInput, Schedule.class);
  }

}
