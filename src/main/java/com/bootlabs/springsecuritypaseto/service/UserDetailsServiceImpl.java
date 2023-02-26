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
        if(user.isPresent()) {
            detailsChecker.check(user.get());
            return user.get();
        } else {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
    }

    @Override
    public Optional<User> getUserByUsername(String usernameValue) {
        // trim username value
        var username = StringUtils.trimToNull(usernameValue);
        if(StringUtils.isNotEmpty(username)) {
            return username.contains("@") ? userRepository.findActiveByEmail(username) : userRepository.findActiveByUsername(username);
        }else {
            return Optional.empty();
        }
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User save(User user) {
        if(userRepository.findByUsername(user.getUsername()).isPresent()){
            throw new DuplicateException("Username Already exist !!");
        }
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new DuplicateException("Email Already exist !!");
        }

        return userRepository.save(user);
    }

    @Override
    public String authenticate(String username, String password) {
        return null;
    }

    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        return null;
    }
}
