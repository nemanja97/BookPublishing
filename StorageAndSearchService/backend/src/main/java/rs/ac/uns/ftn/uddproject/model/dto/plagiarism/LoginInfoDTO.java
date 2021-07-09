package rs.ac.uns.ftn.uddproject.model.dto.plagiarism;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginInfoDTO {

  @NotBlank(message = "Email cannot be empty")
  private String email;

  @NotBlank(message = "Password cannot be empty")
  private String password;
}
