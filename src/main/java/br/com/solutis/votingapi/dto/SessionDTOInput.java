package br.com.solutis.votingapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SessionDTOInput {
  
  private String name;
  private Long scheduleId;
  private int defaultTime = 1;
  private int time;
}
