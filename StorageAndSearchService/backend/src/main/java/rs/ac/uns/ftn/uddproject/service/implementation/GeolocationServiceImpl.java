package rs.ac.uns.ftn.uddproject.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rs.ac.uns.ftn.uddproject.model.dto.CoordinatesDTO;
import rs.ac.uns.ftn.uddproject.service.GeolocationService;

@Service
public class GeolocationServiceImpl implements GeolocationService {

  @Autowired RestTemplate nominativOSMRestTemplate;

  @Bean
  RestTemplate nominativOSMRestTemplate(RestTemplateBuilder builder) {
    return builder.build();
  }

  @Override
  public CoordinatesDTO locate(String country, String city) throws Exception {
    try {
      ResponseEntity<CoordinatesDTO[]> responseEntity =
          nominativOSMRestTemplate.getForEntity(
              String.format(
                  "https://nominatim.openstreetmap.org/search/?city=%s&country=%s&format=json&limit=1",
                  city, country),
              CoordinatesDTO[].class);
      return responseEntity.getBody()[0];
    } catch (Exception ex) {
      throw new Exception("Could not get coordinates");
    }
  }
}
