package lu.ftn.model.dto;

import lu.ftn.model.entity.Genre;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ReaderRegistrationDTO implements Serializable {

    private String username;

    private String password;

    private String name;

    private String surname;

    private String city;

    private String country;

    private String email;

    private List<Genre> genres;

    private boolean betaReader;

    public ReaderRegistrationDTO() {
    }

    public ReaderRegistrationDTO(List<FormSubmissionDTO> dtos) {
        for (FormSubmissionDTO submissionDTO : dtos) {
            switch (submissionDTO.getFieldId()) {
                case "username": this.username = (String) submissionDTO.getFieldValue(); break;
                case "password": this.password = (String) submissionDTO.getFieldValue(); break;
                case "name": this.name = (String) submissionDTO.getFieldValue(); break;
                case "surname": this.surname = (String) submissionDTO.getFieldValue(); break;
                case "city": this.city = (String) submissionDTO.getFieldValue(); break;
                case "country": this.country = (String) submissionDTO.getFieldValue(); break;
                case "email": this.email = (String) submissionDTO.getFieldValue(); break;
                case "genres": this.genres = ((List<String>) submissionDTO.getFieldValue()).stream().map(v -> Genre.valueOf(v)).collect(Collectors.toList()); break;
                case "betaReader": this.betaReader = (boolean) submissionDTO.getFieldValue(); break;
            }
        }
    }

    public ReaderRegistrationDTO(String username, String password, String name, String surname, String city, String country, String email, List<Genre> genres, boolean betaReader) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.city = city;
        this.country = country;
        this.email = email;
        this.genres = genres;
        this.betaReader = betaReader;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public boolean isBetaReader() {
        return betaReader;
    }

    public void setBetaReader(boolean betaReader) {
        this.betaReader = betaReader;
    }

    public HashMap<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("betaReader", this.betaReader);
        map.put("city", this.city);
        map.put("country", this.country);
        map.put("email", this.email);
        map.put("password", this.password);
        map.put("username", this.username);
        map.put("name", this.name);
        map.put("surname", this.surname);
        map.put("genres", this.genres.stream().map(Enum::toString).collect(Collectors.joining(",")));
        return map;
    }

}
