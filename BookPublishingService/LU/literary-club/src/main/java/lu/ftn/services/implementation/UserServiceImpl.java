package lu.ftn.services.implementation;

import lu.ftn.model.entity.Role;
import lu.ftn.model.entity.User;
import lu.ftn.repository.UserRepository;
import lu.ftn.services.UserService;
import org.apache.commons.compress.utils.Lists;
import org.camunda.bpm.engine.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    IdentityService identityService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User findOne(String id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public User findHeadEditor() {
        return userRepository.findByRole(Role.ROLE_HEAD_EDITOR).orElse(null);
    }

    @Override
    public void verifyUser(User user) {
        user.setActive(true);
        save(user);
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public List<User> findAllEditors() {
        List<User> editors = userRepository.findAllByRole(Role.ROLE_EDITOR);
        List<User> headEditors = userRepository.findAllByRole(Role.ROLE_HEAD_EDITOR);
        return Stream.concat(headEditors.stream(), editors.stream())
                .collect(Collectors.toList());
    }

    @Override
    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return save(user);
    }

    @Override
    public User updateProfile(User oldUser, User updatedUser) {
        return null;
    }

    @Override
    public User save(User user) {
        user = userRepository.saveAndFlush(user);
        saveCamundaUser(user);
        return user;
    }

    private void saveCamundaUser(User user) {
        org.camunda.bpm.engine.identity.User camundaUser = identityService.createUserQuery().userId(user.getId()).singleResult();
        if (camundaUser == null) {
            camundaUser = identityService.newUser(user.getId());
        }

        camundaUser.setFirstName(user.getFirstName());
        camundaUser.setLastName(user.getLastName());
        camundaUser.setEmail(user.getEmail());
        camundaUser.setPassword(user.getPassword());
        camundaUser.setId(user.getId());
        identityService.saveUser(camundaUser);

        if (!user.isActive()) {
            switch (user.getRole()) {
                case ROLE_HEAD_EDITOR:
                case ROLE_EDITOR: identityService.createMembership(camundaUser.getId(), "EDITORS"); break;
                case ROLE_WRITER: identityService.createMembership(camundaUser.getId(), "WRITERS"); break;
                case ROLE_READER: identityService.createMembership(camundaUser.getId(), "READERS"); break;
                case ROLE_LECTOR: identityService.createMembership(camundaUser.getId(), "LECTORS"); break;
            }
        }
    }

    @Override
    public void deleteById(String id) {
        userRepository.deleteById(id);
    }


    @Override
    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findById(authentication.getName()).orElse(null);
    }

    public List<User> findAllLectors() {
        return userRepository.findAllByRole(Role.ROLE_LECTOR);
    }
}
