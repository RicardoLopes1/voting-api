package br.com.solutis.votingapi.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionDTOOutput {
  
  private String name;
  private boolean active;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private ScheduleDTOOutput schedule;

}
