package com.company.security;

import com.company.dao.User;
import com.company.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Authenticating {}", login);

        Optional<User> userOptional = userRepository.findByEmail(login);
        if (userOptional.isEmpty()) {
            log.error(String.valueOf(new UsernameNotFoundException("User not found")));
            throw new UsernameNotFoundException("User not found");
        }

        return UserAccount.fromUser(userOptional.get());
    }

}

