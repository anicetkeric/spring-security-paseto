package com.bootlabs.springsecuritypaseto.service;

import com.bootlabs.springsecuritypaseto.common.exception.DuplicateException;
import com.bootlabs.springsecuritypaseto.entities.User;
import com.bootlabs.springsecuritypaseto.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service(value = "userService")
@RequiredArgsConstructor
@Transactional
public class UserDetailsServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();

    /**
     * Load user info by credential
     *
     * @param usernameValue username or email
     * @return UserDetails object
     */
    @Override
    public UserDetails loadUserByUsername(String usernameValue) {
        Optional<User> user = getUserByUsername(usernameValue);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        detailsChecker.check(user.get());
        return user.get();
    }


    /**
     * @param usernameValue username or email
     * @return Optional User
     */
    private Optional<User> getUserByUsername(String usernameValue) {
        // trim username value
        var username = StringUtils.trimToNull(usernameValue);
        if (StringUtils.isEmpty(username)) {
            return Optional.empty();
        }
        return username.contains("@") ? userRepository.findActiveByEmail(username) : userRepository.findActiveByUsername(username);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User save(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new DuplicateException("Username Already exist !!");
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new DuplicateException("Email Already exist !!");
        }

        return userRepository.save(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        return null;
    }
}
