package com.example.ProjectShoes.service.impl;

import com.example.ProjectShoes.common.UserRole;
import com.example.ProjectShoes.dto.request.UserCreatedRequest;
import com.example.ProjectShoes.entity.User;
import com.example.ProjectShoes.repository.AddressRepository;
import com.example.ProjectShoes.repository.UserRepository;
import com.example.ProjectShoes.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "USER-SERVICE")
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    @Override
    @Transactional
    public User createUser(UserCreatedRequest request) {
        User user = new User();
        user.setUserName(request.getUserName());
        user.setPassword(request.getPassword());
        user.setName(request.getName());
        user.setBirthday(request.getBirthday());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setGender(request.getGender());
        user.setAvatar(request.getAvatar());
        user.setStatus(true);
        user.setUserRole(UserRole.ROLE_USER);
        return null;
    }
}
