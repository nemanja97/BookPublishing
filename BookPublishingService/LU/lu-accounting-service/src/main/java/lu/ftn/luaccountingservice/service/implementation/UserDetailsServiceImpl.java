package lu.ftn.luaccountingservice.service.implementation;

import lu.ftn.luaccountingservice.model.entity.User;
import lu.ftn.luaccountingservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        User user = userRepository.findById(id).orElse(null);
        if (user == null){
            throw new UsernameNotFoundException(String.format("No user found with id '%s'.", id));
        }else{
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole().toString()));
            return new org.springframework.security.core.userdetails.User(
                    user.getId(),
                    user.getPassword(),
                    grantedAuthorities);
        }
    }
}
