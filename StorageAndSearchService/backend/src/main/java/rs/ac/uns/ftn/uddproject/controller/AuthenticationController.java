package rs.ac.uns.ftn.uddproject.controller;

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
import rs.ac.uns.ftn.uddproject.model.dto.LoginInfoDTO;
import rs.ac.uns.ftn.uddproject.model.entity.User;
import rs.ac.uns.ftn.uddproject.security.TokenUtils;
import rs.ac.uns.ftn.uddproject.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/auth")
@CrossOrigin
public class AuthenticationController {

  @Autowired AuthenticationManager authenticationManager;

  @Qualifier("userDetailsServiceImpl")
  @Autowired
  UserDetailsService userDetailsService;

  @Autowired UserService userService;

  @Autowired TokenUtils tokenUtils;

  @Validated
  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<String> authentication(@Valid @RequestBody LoginInfoDTO loginInfo) {
    try {
      String token = tryToAuthenticate(loginInfo);
      return new ResponseEntity<>(token, HttpStatus.OK);
    } catch (Exception ex) {
      return new ResponseEntity<>(
          "Login failed; Invalid user email or password", HttpStatus.BAD_REQUEST);
    }
  }

  private String tryToAuthenticate(LoginInfoDTO loginInfo) {
    User user = userService.findUserByEmail(loginInfo.getEmail());
    UsernamePasswordAuthenticationToken token =
        new UsernamePasswordAuthenticationToken(user.getId(), loginInfo.getPassword());
    Authentication authentication = authenticationManager.authenticate(token);
    UserDetails details = userDetailsService.loadUserByUsername(user.getId());
    return tokenUtils.generateToken(details, user.getRole());
  }
}
