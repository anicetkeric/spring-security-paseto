package com.bootlabs.springsecuritypaseto.controller;

import com.bootlabs.springsecuritypaseto.dto.LoginDTO;
import com.bootlabs.springsecuritypaseto.dto.SignupDTO;
import com.bootlabs.springsecuritypaseto.dto.SuccessResponse;
import com.bootlabs.springsecuritypaseto.dto.UserDTO;
import com.bootlabs.springsecuritypaseto.mapper.AppMapper;
import com.bootlabs.springsecuritypaseto.security.TokenProvider;
import com.bootlabs.springsecuritypaseto.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    private final TokenProvider tokenProvider;

    @GetMapping("/user")
    public ResponseEntity<SuccessResponse> getAllUser() {
        List<UserDTO> users = userService.getUsers().stream().map(AppMapper::copyUserEntityToDto).toList();
        return new ResponseEntity<>(new SuccessResponse(users, MessageFormat.format("{0} result found", users.size())), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<SuccessResponse> register(@Valid @RequestBody SignupDTO userDTO) {
        var user = AppMapper.copyUserDtoToEntity(userDTO);
        var encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);

        var newUser = userService.save(user);

        return ResponseEntity.ok(new SuccessResponse(AppMapper.copyUserEntityToDto(newUser), "register Successfully"));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<SuccessResponse> authenticateUser(@Valid @RequestBody LoginDTO loginDTO) {

        var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.username(), loginDTO.password()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new SuccessResponse(jwt, "Login Successfully"));
    }

}
