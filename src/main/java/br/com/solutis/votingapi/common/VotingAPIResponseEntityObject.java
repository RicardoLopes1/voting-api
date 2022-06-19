package br.com.solutis.votingapi.common;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class VotingAPIResponseEntityObject {
  
  private HttpStatus status;
  private String message;
  private Object object;
}
