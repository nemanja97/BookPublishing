package rs.ac.uns.ftn.uddproject.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NominativOSMResponseDTO {

  List<CoordinatesDTO> coordinatesDTOS;
}
