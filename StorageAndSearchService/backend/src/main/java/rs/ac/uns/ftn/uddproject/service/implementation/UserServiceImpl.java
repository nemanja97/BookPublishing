package rs.ac.uns.ftn.uddproject.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.uddproject.model.entity.User;
import rs.ac.uns.ftn.uddproject.repository.jpa.UserRepository;
import rs.ac.uns.ftn.uddproject.service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

  @Autowired UserRepository userRepository;

  @Autowired PasswordEncoder passwordEncoder;

  @Override
  public List<User> findAll() {
    return userRepository.findAll();
  }

  @Override
  public User findUserById(String id) {
    return userRepository.findById(id).orElse(null);
  }

  @Override
  public User findUserByEmail(String email) {
    return userRepository.findByEmail(email).orElse(null);
  }

  @Override
  public User registerUser(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    return userRepository.save(user);
  }

  @Override
  public User save(User user) {
    return userRepository.save(user);
  }
}
