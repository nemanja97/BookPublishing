package rs.ac.uns.ftn.uddproject.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import rs.ac.uns.ftn.uddproject.model.dto.BookInfoDTO;

public class ConversionHelper {

  public static BookInfoDTO convertRequestObjectToBookInfoDTO(String value)
      throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(value, BookInfoDTO.class);
  }
}
