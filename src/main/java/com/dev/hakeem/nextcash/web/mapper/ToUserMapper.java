package com.dev.hakeem.nextcash.web.mapper;

import com.dev.hakeem.nextcash.entity.User;
import com.dev.hakeem.nextcash.web.request.UserCreateDTO;
import com.dev.hakeem.nextcash.web.response.UserCreateResponse;

public class ToUserMapper {

    public static User CreateUserTO(UserCreateDTO createDTO){
        User user = new User();

        user.setId(createDTO.getId());
        user.setEmail(createDTO.getEmail());
        user.setUsername(createDTO.getUsername());
        user.setPassword(createDTO.getPassword());
        return user;
    }

    public static UserCreateResponse ResponseUserTO(User user){
        UserCreateResponse createResponse = new UserCreateResponse();

        createResponse.setId(user.getId());
        createResponse.setEmail(user.getEmail());
        createResponse.setUsername(user.getUsername());

        return createResponse;
    }
}
