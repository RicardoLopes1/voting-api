package br.com.solutis.votingapi.common;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.solutis.votingapi.dto.AssociateDTO;
import br.com.solutis.votingapi.entities.Associate;

@Configuration
public class ModelMapperConfig {
  
  @Bean
  public ModelMapper modelMapper() {
    var modelMapper = new ModelMapper();

    /* CPF formater - Associate */
    Converter<String, String> addCharactersInCpfConverter = context -> {
      var cpf = context.getSource();
      var cpfWithCharacters = cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
      return cpfWithCharacters;
    };

    Converter<String, String> removeCharactersInCpfConverter = context -> {
      var cpf = context.getSource();
      var cpfWithoutCharacters = cpf.replaceAll("\\D", "");
      return cpfWithoutCharacters;
    };
    
    modelMapper.createTypeMap(Associate.class, AssociateDTO.class)
      .addMappings(mapper -> mapper.using(addCharactersInCpfConverter).map(Associate::getCpf, AssociateDTO::setCpf));

    modelMapper.createTypeMap(AssociateDTO.class, Associate.class)
      .addMappings(mapper -> mapper.using(removeCharactersInCpfConverter).map(AssociateDTO::getCpf, Associate::setCpf));  
    /* ************************ */ 
    
    return modelMapper;
  }
  
}
