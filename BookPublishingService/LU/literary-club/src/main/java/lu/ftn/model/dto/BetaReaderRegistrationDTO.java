package lu.ftn.model.dto;

import lu.ftn.model.entity.Genre;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class BetaReaderRegistrationDTO implements Serializable {

    private List<Genre> betaGenres;

    public BetaReaderRegistrationDTO(List<FormSubmissionDTO> dtos) {
        for (FormSubmissionDTO submissionDTO : dtos) {
            switch (submissionDTO.getFieldId()) {
                case "betaGenres": this.betaGenres = ((List<String>) submissionDTO.getFieldValue()).stream().map(v -> Genre.valueOf(v)).collect(Collectors.toList()); break;
            }
        }
    }

    public BetaReaderRegistrationDTO() {
    }

    public List<Genre> getBetaGenres() {
        return betaGenres;
    }

    public void setBetaGenres(List<Genre> betaGenres) {
        this.betaGenres = betaGenres;
    }

    public HashMap<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("betaGenres", this.betaGenres.stream().map(Enum::toString).collect(Collectors.joining(",")));
        return map;
    }
}
