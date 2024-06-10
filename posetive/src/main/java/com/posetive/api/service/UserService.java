package com.posetive.api.service;

import com.posetive.api.repository.UserRepository;
import com.posetive.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Boolean existsByLoginId(String loginId) { return userRepository.existsByLoginId(loginId); }

    public User findByLoginId(String loginId) { return userRepository.findByLoginId(loginId); }
}
