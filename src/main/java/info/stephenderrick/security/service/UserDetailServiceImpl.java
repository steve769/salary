package info.stephenderrick.security.service;

import info.stephenderrick.security.entity.User;
import info.stephenderrick.security.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;


@Service
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Use email as username
        Optional<User> userOptional = userRepository.findByEmail(username);
        User user = userOptional.orElseThrow(()->
                new UsernameNotFoundException("Username not found! Please check your email"));

        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(), user.isEnabled(), true,
                true, true, getGrantedAuthorities("USER"));
    }

    private Collection<? extends GrantedAuthority> getGrantedAuthorities(String role) {

        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }
}
