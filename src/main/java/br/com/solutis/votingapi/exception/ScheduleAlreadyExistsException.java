package br.com.solutis.votingapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST , reason = "Schedule already exists.")
public class ScheduleAlreadyExistsException extends RuntimeException {
  
}
