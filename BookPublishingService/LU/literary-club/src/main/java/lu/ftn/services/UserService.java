package lu.ftn.services;

import lu.ftn.model.entity.User;

import java.util.List;

public interface UserService {

    User findOne(String id);

    User findUserByEmail(String email);

    User findUserByUsername(String username);

    User findHeadEditor();

    void verifyUser(User user);

    List<User> findAll();

    List<User> findAllEditors();

    List<User> findAllLectors();

    User registerUser(User user);

    User updateProfile(User oldUser, User updatedUser);

    User save(User user);

    void deleteById(String id);

    User getLoggedInUser();

}
