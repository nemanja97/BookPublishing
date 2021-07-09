package lu.ftn.services.tasks;

import lu.ftn.helper.Converter;
import lu.ftn.model.dto.BetaReaderRegistrationDTO;
import lu.ftn.model.dto.ReaderRegistrationDTO;
import lu.ftn.model.dto.WriterRegistrationDTO;
import lu.ftn.model.entity.*;
import lu.ftn.services.UserService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.impl.form.validator.FormFieldValidatorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ValidateRegistrationFields implements JavaDelegate {

    @Autowired
    UserService userService;

    @Override
    public void execute(DelegateExecution delegateExecution) {
        System.out.println("CAMUNDA - ValidateRegistrationFields");
        Role registrationUserRole = (Role) delegateExecution.getVariable("registration_type");

        if (registrationUserRole.equals(Role.ROLE_WRITER)) {
            WriterRegistrationDTO registrationDTO = (WriterRegistrationDTO) delegateExecution.getVariable("registration_form");
            Writer writer = Converter.writerRegistrationDTOToWriter(registrationDTO);

            try {
                validateWriterFields(writer);
                delegateExecution.setVariable("fieldsAreValid", true);
            } catch (FormFieldValidatorException ex) {
                delegateExecution.setVariable("fieldsAreValid", false);
                throw ex;
            }

        } else {
            ReaderRegistrationDTO registrationDTO = (ReaderRegistrationDTO) delegateExecution.getVariable("registration_form");
            BetaReaderRegistrationDTO betaReaderRegistrationDTO = (BetaReaderRegistrationDTO) delegateExecution.getVariable("registration_form_beta_reader");
            Reader reader = Converter.readerRegistrationDTOToUser(registrationDTO, betaReaderRegistrationDTO);

            try {
                validateReaderFields(reader);
                delegateExecution.setVariable("fieldsAreValid", true);
            } catch (FormFieldValidatorException ex) {
                delegateExecution.setVariable("fieldsAreValid", false);
                throw ex;
            }
        }

    }

    private void validateReaderFields(Reader reader) {
        validateUsername(reader.getUsername());
        validatePassword(reader.getPassword());
        validateCity(reader.getCity());
        validateCountry(reader.getCountry());
        validateName(reader.getName());
        validateSurname(reader.getSurname());
        validateGenres(reader.getGenresInterestedIn());
        if (reader.isBetaReader())
            validateGenres(reader.getBetaGenresInterestedIn());
        validateEmail(reader.getEmail());
    }

    private void validateWriterFields(Writer writer) {
        validateUsername(writer.getUsername());
        validatePassword(writer.getPassword());
        validateCity(writer.getCity());
        validateCountry(writer.getCountry());
        validateName(writer.getName());
        validateSurname(writer.getSurname());
        validateGenres(writer.getGenresInterestedIn());
        validateEmail(writer.getEmail());
    }

    private void validateUsername(String username) {
        User user = userService.findUserByUsername(username);

        if (user != null) {
            throw new FormFieldValidatorException("1A", "username", "invalid", username, "Username taken");
        }
        if (username == null || username.isEmpty()) {
            throw new FormFieldValidatorException("1B", "username", "invalid", username, "Username is empty");
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new FormFieldValidatorException("2A", "password", "invalid", password, "Password is empty");
        }
    }

    private void validateCity(String city) {
        if (city == null || city.isEmpty()) {
            throw new FormFieldValidatorException("3A", "city", "invalid", city, "City is empty");
        }
    }

    private void validateCountry(String country) {
        if (country == null || country.isEmpty()) {
            throw new FormFieldValidatorException("4A", "country", "invalid", country, "Country is empty");
        }
    }

    private void validateName(String name) {
        if (name == null || name.isEmpty()) {
            throw new FormFieldValidatorException("5A", "name", "invalid", name, "Name is empty");
        }
    }

    private void validateSurname(String surname) {
        if (surname == null || surname.isEmpty()) {
            throw new FormFieldValidatorException("6A", "surname", "invalid", surname, "Surname is empty");
        }
    }

    private void validateGenres(List<Genre> genres) {
        if (genres == null || genres.isEmpty()) {
            throw new FormFieldValidatorException("7A", "genres", "invalid", genres, "Must choose at least one genre");
        }
    }

    private void validateEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new FormFieldValidatorException("8A", "email", "invalid", email, "Email is empty");
        }

        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        if (!matcher.matches()) {
            throw new FormFieldValidatorException("8B", "email", "invalid", email, "Email is in invalid format");
        }

        User user = userService.findUserByEmail(email);
        if (user != null) {
            throw new FormFieldValidatorException("8C", "email", "invalid", email, "Email taken");
        }

    }
}
