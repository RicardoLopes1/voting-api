package br.com.solutis.votingapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST , reason = "Session already exists with this Schedule.")
public class SessionAlreadyExistsException extends RuntimeException {

}
