package rs.ac.uns.ftn.uddproject.service;

import rs.ac.uns.ftn.uddproject.model.entity.User;

import java.util.List;

public interface UserService {

  List<User> findAll();

  User findUserById(String id);

  User findUserByEmail(String email);

  User registerUser(User user);

  User save(User user);
}
