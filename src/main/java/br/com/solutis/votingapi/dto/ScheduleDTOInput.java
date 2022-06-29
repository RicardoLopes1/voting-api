package br.com.solutis.votingapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleDTOInput {
  
  private String name;
  private String description;
  private Long creatorId;
  private String createdBy;

}
