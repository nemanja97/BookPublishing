package lu.ftn.kpservice.service.implementation;

import lu.ftn.kpservice.model.entity.User;
import lu.ftn.kpservice.repository.UserRepository;
import lu.ftn.kpservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return saveUser(user);
    }

    @Override
    public User verifyUser(String id) {
        User user = getUserById(id);
        user.setActive(true);
        return saveUser(user);
    }

    @Override
    public User changePassword(String id, String password) {
        User user = getUserById(id);
        user.setPassword(password);
        return saveUser(user);
    }

    @Override
    public User getUserById(String id) {
        if (id == null) {
            return null;
        }
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}
