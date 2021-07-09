package lu.ftn.luaccountingservice.service;

import lu.ftn.luaccountingservice.model.entity.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findUserById(String id);

    User findUserByEmail(String email);

    User registerUser(User user);

    User save(User user);
}
