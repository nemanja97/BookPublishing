package rs.ac.uns.ftn.uddproject.service;

import rs.ac.uns.ftn.uddproject.model.dto.CoordinatesDTO;

public interface GeolocationService {

  CoordinatesDTO locate(String country, String city) throws Exception;
}
