package lu.ftn.kpservice.service;

import lu.ftn.kpservice.model.entity.User;

public interface UserService {

    User saveUser(User user);

    User registerUser(User user);

    User verifyUser(String id);

    User changePassword(String id, String password);

    User getUserById(String id);

    User getUserByEmail(String email);
}
