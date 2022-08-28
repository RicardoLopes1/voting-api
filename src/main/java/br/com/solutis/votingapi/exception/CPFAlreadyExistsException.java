package br.com.solutis.votingapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "CPF Already Exists.")
public class CPFAlreadyExistsException extends RuntimeException {
  
}
