package lu.ftn.model.dto;

import lu.ftn.model.entity.Genre;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class BookPublishingDTO implements Serializable {
    private String title;
    private List<Genre> genres;
    private String synopsis;

    public BookPublishingDTO() {
    }
    public BookPublishingDTO(List<FormSubmissionDTO> dtos) {
        for (FormSubmissionDTO submissionDTO : dtos) {
            switch (submissionDTO.getFieldId()) {
                case "title":{
                    this.title = (String) submissionDTO.getFieldValue();
                    break;
                }
                case "genres":{
                    this.genres = ((List<String>) submissionDTO.getFieldValue()).stream().map(v -> Genre.valueOf(v)).collect(Collectors.toList());
                    break;
                }
                case "synopsis":{
                    this.synopsis = (String) submissionDTO.getFieldValue();
                }
            }
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public HashMap<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("title", title);
        StringBuilder g = new StringBuilder(genres.get(0).toString());
        for (int i = 1; i < genres.size(); i++) {
            g.append(",").append(genres.get(i));
        }
        map.put("genres", g.toString());
        map.put("synopsis", synopsis);
        return map;
    }
}
