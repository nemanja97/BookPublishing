package lu.ftn.kpservice.controller;

import lu.ftn.kpservice.exceptions.EmailAlreadyExistsException;
import lu.ftn.kpservice.helper.Converter;
import lu.ftn.kpservice.model.dto.ChangePasswordRequestDTO;
import lu.ftn.kpservice.model.dto.UserRegistrationDTO;
import lu.ftn.kpservice.model.entity.User;
import lu.ftn.kpservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(path = "register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> storeAdminRegistration(@RequestBody UserRegistrationDTO dto) {
        if (userService.getUserByEmail(dto.getEmail()) != null) {
            throw new EmailAlreadyExistsException();
        }

        User user = Converter.userRegistrationDTOtoUser(dto);
        userService.registerUser(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "verify/{id}", produces = "application/json")
    public ResponseEntity<Object> verifyUser(@PathVariable String id) {
        userService.verifyUser(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "change_password/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> verifyUser(@PathVariable String id, @RequestBody ChangePasswordRequestDTO dto) {
        userService.changePassword(id, dto.getPassword());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
