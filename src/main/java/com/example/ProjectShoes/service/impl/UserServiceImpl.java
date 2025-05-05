package com.example.ProjectShoes.service.impl;

import com.example.ProjectShoes.common.UserStatus;
import com.example.ProjectShoes.dto.request.AddressRequest;
import com.example.ProjectShoes.dto.request.UpdateUserRequest;
import com.example.ProjectShoes.dto.request.UserRequest;
import com.example.ProjectShoes.dto.response.AddressResponse;
import com.example.ProjectShoes.dto.response.UpdateUserResponse;
import com.example.ProjectShoes.dto.response.UserResponse;
import com.example.ProjectShoes.entity.Address;
import com.example.ProjectShoes.entity.Role;
import com.example.ProjectShoes.entity.User;
import com.example.ProjectShoes.exception.AppException;
import com.example.ProjectShoes.exception.ErrorCode;
import com.example.ProjectShoes.repository.AddressRepository;
import com.example.ProjectShoes.repository.RoleRepository;
import com.example.ProjectShoes.repository.UserRepository;
import com.example.ProjectShoes.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "USER-SERVICE")
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public UserResponse createUser(UserRequest request) {
        User user = new User();
        user.setUserName(request.getUserName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setName(request.getName());
        user.setBirthday(request.getBirthday());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setGender(request.getGender());
        user.setAvatar(request.getAvatar());
        user.setStatus(UserStatus.ACTIVE);

        Role role = roleRepository.findById(request.getRoleId()).orElseThrow(() -> new RuntimeException("Role not found"));
        log.info("Role: {}", role);
        user.setRole(role);

        User result = userRepository.save(user);
        log.info("Save user: {}", user);

        AddressResponse addressResponse = null;
        if (result.getId() != null) {
            log.info("User id : {}", result.getId());
            AddressRequest addressRequest = request.getAddress();

            Address address = new Address();
            address.setProvince(addressRequest.getProvince());
            address.setDistrict(addressRequest.getDistrict());
            address.setWard(addressRequest.getWard());
            address.setSpecificAddress(addressRequest.getSpecificAddress());
            address.setDefaultAddress(true);
            address.setUser(result);

            Address addressResult = addressRepository.save(address);

            addressResponse = AddressResponse.builder()
                    .province(addressResult.getProvince())
                    .district(addressResult.getDistrict())
                    .ward(addressResult.getWard())
                    .specificAddress(addressResult.getSpecificAddress())
                    .build();

        }

        return UserResponse.builder()
                .id(result.getId())
                .userName(result.getUserName())
                .name(result.getName())
                .email(result.getEmail())
                .phoneNumber(result.getPhoneNumber())
                .gender(result.getGender())
                .address(addressResponse)
                .build();
    }

    @Override
    public UpdateUserResponse updateUser(Long id, UpdateUserRequest request) {
        User result = userRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));
        result.setName(request.getName());
        result.setBirthday(request.getBirthday());
        result.setEmail(request.getEmail());
        result.setPhoneNumber(request.getPhoneNumber());
        result.setGender(request.getGender());
        result.setAvatar(request.getAvatar());
        userRepository.save(result);

        return UpdateUserResponse.builder()
                .name(result.getName())
                .birthday(request.getBirthday())
                .email(result.getEmail())
                .phoneNumber(result.getPhoneNumber())
                .gender(result.getGender())
                .avatar(request.getAvatar()).build();
    }


    @Override
    public UserResponse findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));

        Address defaultAddress = user.getAddress().stream()
                .filter(Address::isDefaultAddress) // Lọc địa chỉ mặc định
                .findFirst() // Trả về Optional<Address>
                .orElse(null);
        AddressResponse addressResponse =  AddressResponse.builder()
                .province(defaultAddress.getProvince())
                .district(defaultAddress.getDistrict())
                .ward(defaultAddress.getWard())
                .specificAddress(defaultAddress.getSpecificAddress())
                .build();

        return UserResponse.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .name(user.getName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .gender(user.getGender())
                .address(addressResponse)
                .build();
    }

}
