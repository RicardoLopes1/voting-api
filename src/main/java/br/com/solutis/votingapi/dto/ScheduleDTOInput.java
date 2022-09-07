package br.com.solutis.votingapi.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDTOInput {
  
  @NotBlank(message = "Schedule Name cannot be blank")
  @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters")
  private String name;

  private String description;

  @NotNull(message = "Schedule creatorId cannot be null")
  private Long creatorId;

  @NotBlank(message = "Schedule createdBy cannot be blank")
  @Size(min = 1, max = 50, message = "createdBy must be between 1 and 50 characters")
  private String createdBy;

}
