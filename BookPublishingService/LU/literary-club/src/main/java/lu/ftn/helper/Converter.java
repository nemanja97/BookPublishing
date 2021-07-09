package lu.ftn.helper;

import lu.ftn.model.dto.BetaReaderRegistrationDTO;
import lu.ftn.model.dto.FormSubmissionDTO;
import lu.ftn.model.dto.ReaderRegistrationDTO;
import lu.ftn.model.dto.WriterRegistrationDTO;
import lu.ftn.model.entity.Reader;
import lu.ftn.model.entity.Role;
import lu.ftn.model.entity.User;
import lu.ftn.model.entity.Writer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Converter {

    public static HashMap<String, Object> FormSubmissionDTOStoMap(List<FormSubmissionDTO> submissionDTOS) {
        HashMap<String, Object> map = new HashMap<>();
        for (FormSubmissionDTO formSubmissionDTO : submissionDTOS) {
            map.put(formSubmissionDTO.getFieldId(), formSubmissionDTO.getFieldValue());
        }
        return map;
    }

    public static HashMap<String, Object> RegistrationFormDTOToMap(List<FormSubmissionDTO> submissionDTOS) {
        HashMap<String, Object> map = new HashMap<>();
        for (FormSubmissionDTO formSubmissionDTO : submissionDTOS) {
            map.put(formSubmissionDTO.getFieldId(), formSubmissionDTO.getFieldValue());
        }
        return map;
    }

    public static Writer writerRegistrationDTOToWriter(WriterRegistrationDTO writerRegistrationDTO) {
        return new Writer("",
                writerRegistrationDTO.getName(),
                writerRegistrationDTO.getSurname(),
                writerRegistrationDTO.getCity(),
                writerRegistrationDTO.getCountry(),
                writerRegistrationDTO.getEmail(),
                writerRegistrationDTO.getUsername(),
                writerRegistrationDTO.getPassword(),
                writerRegistrationDTO.getGenres(),
                new HashSet<>(),
                new HashSet<>()
        );
    }

    public static Reader readerRegistrationDTOToUser(ReaderRegistrationDTO readerRegistrationDTO) {

        return new Reader("",
                readerRegistrationDTO.getName(),
                readerRegistrationDTO.getSurname(),
                readerRegistrationDTO.getCity(),
                readerRegistrationDTO.getCountry(),
                readerRegistrationDTO.getEmail(),
                readerRegistrationDTO.getUsername(),
                readerRegistrationDTO.getPassword(),
                readerRegistrationDTO.getGenres(),
                null,
                readerRegistrationDTO.isBetaReader()
        );
    }

    public static Reader readerRegistrationDTOToUser(ReaderRegistrationDTO readerRegistrationDTO,
                                                     BetaReaderRegistrationDTO betaReaderRegistrationDTO) {
        if (betaReaderRegistrationDTO == null)
            return readerRegistrationDTOToUser(readerRegistrationDTO);

        return new Reader("",
                readerRegistrationDTO.getName(),
                readerRegistrationDTO.getSurname(),
                readerRegistrationDTO.getCity(),
                readerRegistrationDTO.getCountry(),
                readerRegistrationDTO.getEmail(),
                readerRegistrationDTO.getUsername(),
                readerRegistrationDTO.getPassword(),
                readerRegistrationDTO.getGenres(),
                betaReaderRegistrationDTO.getBetaGenres(),
                readerRegistrationDTO.isBetaReader()
        );
    }

}
