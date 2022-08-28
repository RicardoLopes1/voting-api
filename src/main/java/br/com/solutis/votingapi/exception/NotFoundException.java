package br.com.solutis.votingapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Resource Not Found.")
public class NotFoundException extends RuntimeException {
  
}
