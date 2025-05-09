package com.example.ProjectShoes.service.impl;

import com.example.ProjectShoes.common.UserStatus;
import com.example.ProjectShoes.dto.request.AddressRequest;
import com.example.ProjectShoes.dto.request.UpdateUserRequest;
import com.example.ProjectShoes.dto.request.UserRequest;
import com.example.ProjectShoes.dto.response.AddressResponse;
import com.example.ProjectShoes.dto.response.PageResponse;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        if(userRepository.existsByEmail(request.getEmail())){
            throw  new AppException(ErrorCode.EMAIL_EXISTED);
        }
        if(userRepository.existsByUserName(request.getUserName())){
            throw  new AppException(ErrorCode.USER_EXISTED);
        }
        if(userRepository.existsByPhoneNumber(request.getPhoneNumber())){
            throw new AppException(ErrorCode.PHONE_NUMBER_EXISTED);
        }
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
        if(userRepository.existsByEmail(request.getEmail())){
            throw  new AppException(ErrorCode.EMAIL_EXISTED);
        }
        if(userRepository.existsByPhoneNumber(request.getPhoneNumber())){
            throw new AppException(ErrorCode.PHONE_NUMBER_EXISTED);
        }
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

    @Override
    public PageResponse<UserResponse> finAll(String keyword,String sort, int pageNumber, int pageSize) {
        // Sorting
        Sort.Order order = new Sort.Order(Sort.Direction.ASC,"id");
        if (StringUtils.hasLength(sort)) {
            Pattern pattern = Pattern.compile("(\\w+?)(:)(.*)"); // tencot:asc|desc
            Matcher matcher = pattern.matcher(sort);
            if (matcher.find()) {
                String columnName = matcher.group(1);
                if (matcher.group(3).equalsIgnoreCase("asc")) {
                    order = new Sort.Order(Sort.Direction.ASC, columnName);
                } else {
                    order = new Sort.Order(Sort.Direction.DESC, columnName);
                }
            }
        }
        Pageable pageable = PageRequest.of(pageNumber -1 ,pageSize,Sort.by(order));
        Page<User> userPage = userRepository.searchByKeyword(  keyword,pageable);

        List<User> userList= userPage.getContent();
        List<UserResponse> userResponseList = new ArrayList<>();

        for (User user : userList) {
            UserResponse response = new UserResponse();
            response.setId(user.getId());
            response.setUserName(user.getUserName());
            response.setName(user.getName());
            response.setBirthday(user.getBirthday());
            response.setEmail(user.getEmail());
            response.setPhoneNumber(user.getPhoneNumber());
            response.setGender(user.getGender());
            userResponseList.add(response);
        }

        PageResponse<UserResponse> pageResponse = new PageResponse<>();
        pageResponse.setPageNumber(userPage.getNumber());
        pageResponse.setPageSize(userPage.getSize());
        pageResponse.setTotalElements(userPage.getTotalElements());
        pageResponse.setTotalPages(userPage.getTotalPages());
        pageResponse.setData(userResponseList);

        log.info("{} :",userPage.getContent());
        return pageResponse;
    }

}
