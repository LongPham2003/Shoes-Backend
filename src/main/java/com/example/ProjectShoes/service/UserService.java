package com.example.ProjectShoes.service;

import com.example.ProjectShoes.dto.request.UserCreatedRequest;
import com.example.ProjectShoes.entity.User;

public interface UserService {
    User createUser(UserCreatedRequest request);
}
