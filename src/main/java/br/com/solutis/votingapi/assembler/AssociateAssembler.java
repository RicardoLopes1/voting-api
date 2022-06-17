package br.com.solutis.votingapi.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import br.com.solutis.votingapi.dto.AssociateDTO;
import br.com.solutis.votingapi.entities.Associate;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AssociateAssembler {
  
  private final ModelMapper modelMapper;

  public AssociateDTO toDto(Associate associate) {
    return modelMapper.map(associate, AssociateDTO.class);
  }

  public Page<AssociateDTO> toPageDto(Page<Associate> associates) {
    return associates.map(this::toDto);       
  }

  public Associate toEntity(AssociateDTO associateDto) {
    return modelMapper.map(associateDto, Associate.class);
  }
}
