package br.com.solutis.votingapi.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import br.com.solutis.votingapi.dto.SessionDTOInput;
import br.com.solutis.votingapi.dto.SessionDTOOutput;
import br.com.solutis.votingapi.entities.Session;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class SessionMapper {
  
  private final ModelMapper mapper;

  public SessionDTOOutput toDTOOutput(Session session) {
    return mapper.map(session, SessionDTOOutput.class);
  }

  public Session toEntity(SessionDTOInput sessionDtoInput) {
    return mapper.map(sessionDtoInput, Session.class);
  }

  public Page<SessionDTOOutput> toPageDTOOutput(Page<Session> sessions) {
    return sessions.map(this::toDTOOutput);
  }

}
