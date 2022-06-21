package br.com.solutis.votingapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VotingDTOInput {
  
  private Long sessionId;
  private Long scheduleId;
  private Long associateId;
  private String vote;

}
