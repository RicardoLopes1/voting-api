package br.com.solutis.votingapi.dto;

import br.com.solutis.votingapi.entities.Schedule;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class VotesCountingPerSessionDTO {
  
  private long sessionId;
  private double votesYes;
  private double percentageYes;
  private double votesNo;
  private double percentageNo;
  private boolean scheduleWinner;
  private Schedule schedule;
  
}
