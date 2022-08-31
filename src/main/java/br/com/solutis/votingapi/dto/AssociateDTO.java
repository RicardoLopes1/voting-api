package br.com.solutis.votingapi.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssociateDTO {
  
  @NotBlank(message = "Name cannot be blank")
  @Size(min = 2, max = 255, message = "Name must be between 2 and 255 characters")
  private String name;
  
  @NotBlank(message = "CPF cannot be blank")
  @NotNull(message = "CPF cannot be null")
  @Pattern(regexp = "^[0-9]{3}\\.{0,1}[0-9]{3}\\.{0,1}[0-9]{3}\\-{0,1}[0-9]{2}$", message = "CPF is not standard")
  @CPF(message = "CPF is invalid")
  private String cpf;
  
  @NotBlank(message = "Email cannot be blank")
  @NotNull(message = "Email cannot be null")
  @Pattern(regexp = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$", message = "E-mail is not standard")
  @Email(message = "E-mail is invalid")
  private String email;

  /* To add a Birth date
  @Past(message = "Birth date is not in the past")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate birthDate;
  */

}
