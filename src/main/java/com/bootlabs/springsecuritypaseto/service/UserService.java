package com.bootlabs.springsecuritypaseto.service;


import com.bootlabs.springsecuritypaseto.entities.User;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

/**
 * <h2>UserService</h2>
 *
 * @author aek
 * <p>
 * Description:
 */
public interface UserService extends UserDetailsService, UserDetailsPasswordService{

    /**
     * @return list of User
     */
    List<User> getUsers();

    /**
     * @param usernameValue username or email
     * @return Optional User
     */
    Optional<User> getUserByUsername(String usernameValue);

    /**
     * @param user ussr object
     * @return user saved or updated
     */
    User save(User user);
    String authenticate(String username, String password);
}
