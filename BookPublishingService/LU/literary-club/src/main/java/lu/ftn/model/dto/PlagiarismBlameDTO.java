package lu.ftn.model.dto;

import lu.ftn.model.entity.Genre;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class PlagiarismBlameDTO implements Serializable {

    @NotNull
    @NotBlank
    private String plagiarism_book;

    @NotNull
    @NotBlank
    private String plagiarism_author;

    @NotNull
    @NotBlank
    private String original_book;

    @NotNull
    @NotBlank
    private String original_author;

    public PlagiarismBlameDTO() {
    }

    public PlagiarismBlameDTO(List<FormSubmissionDTO> dtos) {
        for (FormSubmissionDTO submissionDTO : dtos) {
            switch (submissionDTO.getFieldId()) {
                case "plagiarism_book": this.plagiarism_book = (String) submissionDTO.getFieldValue(); break;
                case "plagiarism_author": this.plagiarism_author = (String) submissionDTO.getFieldValue(); break;
                case "original_book": this.original_book = (String) submissionDTO.getFieldValue(); break;
                case "original_author": this.original_author = (String) submissionDTO.getFieldValue(); break;
            }
        }
    }

    public PlagiarismBlameDTO(@NotNull @NotBlank String plagiarism_book, @NotNull @NotBlank String plagiarism_author, @NotNull @NotBlank String original_book, @NotNull @NotBlank String original_author) {
        this.plagiarism_book = plagiarism_book;
        this.plagiarism_author = plagiarism_author;
        this.original_book = original_book;
        this.original_author = original_author;
    }

    public String getPlagiarism_book() {
        return plagiarism_book;
    }

    public void setPlagiarism_book(String plagiarism_book) {
        this.plagiarism_book = plagiarism_book;
    }

    public String getPlagiarism_author() {
        return plagiarism_author;
    }

    public void setPlagiarism_author(String plagiarism_author) {
        this.plagiarism_author = plagiarism_author;
    }

    public String getOriginal_book() {
        return original_book;
    }

    public void setOriginal_book(String original_book) {
        this.original_book = original_book;
    }

    public String getOriginal_author() {
        return original_author;
    }

    public void setOriginal_author(String original_author) {
        this.original_author = original_author;
    }

    public HashMap<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("plagiarism_book", this.plagiarism_book);
        map.put("plagiarism_author", this.plagiarism_author);
        map.put("original_book", this.original_book);
        map.put("original_author", this.original_author);
        return map;
    }

}
