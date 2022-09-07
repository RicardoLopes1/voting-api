package br.com.solutis.votingapi.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionDTOInput {
  
  @NotBlank(message = "Name cannot be blank")
  @Size(min = 1, max = 50, message = "Session Name must be between 1 and 50 characters")
  private String name;

  @NotNull(message = "Session - Schedule Id cannot be null")
  private Long scheduleId;
  
  private int defaultTime = 1;
  private int time;
}
