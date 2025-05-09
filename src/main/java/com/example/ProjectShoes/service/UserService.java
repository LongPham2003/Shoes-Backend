package com.example.ProjectShoes.service;

import com.example.ProjectShoes.dto.request.UpdateUserRequest;
import com.example.ProjectShoes.dto.request.UserRequest;
import com.example.ProjectShoes.dto.response.PageResponse;
import com.example.ProjectShoes.dto.response.UpdateUserResponse;
import com.example.ProjectShoes.dto.response.UserResponse;

public interface UserService {
    UserResponse createUser(UserRequest request);

    UpdateUserResponse updateUser(Long id, UpdateUserRequest request);

    UserResponse findById(Long id);

    PageResponse<UserResponse> finAll(String keyword,String sort, int pageNumber, int pageSize);
}
