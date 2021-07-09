package rs.ac.uns.ftn.uddproject.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.ac.uns.ftn.uddproject.model.enums.Genre;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookInfoDTO {

  @NotBlank(message = "Email cannot be empty")
  private String title;

  @PositiveOrZero private BigDecimal price;

  private String coverImageInBase64;

  @NotNull @NotEmpty private Set<Genre> genres;
}
