package com.example.ProjectShoes.controller;


import com.example.ProjectShoes.dto.request.UpdateUserRequest;
import com.example.ProjectShoes.dto.response.ApiResponse;
import com.example.ProjectShoes.dto.request.UserRequest;
import com.example.ProjectShoes.dto.response.PageResponse;
import com.example.ProjectShoes.dto.response.UpdateUserResponse;
import com.example.ProjectShoes.dto.response.UserResponse;
import com.example.ProjectShoes.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j(topic = "USER-CONTROLLER")
@Tag(name = "USER-CONTROLLER")
public class UserController {

    private final UserService userService;

    @GetMapping("/search")
    public ApiResponse<PageResponse<UserResponse>> UserSearch(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String sort,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size
    ){
        return ApiResponse.<PageResponse<UserResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .result(userService.finAll(keyword,sort,page,size))
                .build();
    }

    @PostMapping("/create")
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserRequest request){
        return ApiResponse.<UserResponse>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .result(userService.createUser(request))
                .build();
    }

    @PostMapping("/update/{id}")
    public ApiResponse<UpdateUserResponse> createUser(@PathVariable("id") Long id, @RequestBody @Valid UpdateUserRequest request){
        return ApiResponse.<UpdateUserResponse>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .result(userService.updateUser(id,request))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponse> findUserById(@PathVariable("id") long id){
        return ApiResponse.<UserResponse>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .result(userService.findById(id))
                .build();
    }
}
