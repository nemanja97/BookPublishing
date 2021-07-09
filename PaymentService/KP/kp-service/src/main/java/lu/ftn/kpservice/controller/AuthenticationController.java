package lu.ftn.kpservice.controller;

import lu.ftn.kpservice.model.dto.LoginInfoDTO;
import lu.ftn.kpservice.model.entity.User;
import lu.ftn.kpservice.security.TokenUtils;
import lu.ftn.kpservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping(value = "/api/auth")
public class AuthenticationController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Qualifier("userDetailsServiceImpl")
    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    UserService userService;

    @Autowired
    TokenUtils tokenUtils;

    @Validated
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> authentication(@Valid @RequestBody LoginInfoDTO loginInfo){
        try {
            String token = tryToAuthenticate(loginInfo);
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Login failed; Invalid user email or password", HttpStatus.BAD_REQUEST);
        }
    }

    private String tryToAuthenticate(LoginInfoDTO loginInfo) {
        User user = userService.getUserByEmail(loginInfo.getEmail());
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                user.getId(), loginInfo.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);
        UserDetails details = userDetailsService.loadUserByUsername(user.getId());
        return tokenUtils.generateToken(details, user.getRole() );
    }
}

