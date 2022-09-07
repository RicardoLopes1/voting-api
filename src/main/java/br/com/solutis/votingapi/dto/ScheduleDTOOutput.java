package br.com.solutis.votingapi.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleDTOOutput {
  
  private String name;
  private String description;
  private String createdBy;
  private LocalDateTime createdDate;
  
}
