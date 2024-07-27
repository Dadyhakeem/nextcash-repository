package com.dev.hakeem.nextcash.web.mapper;

import com.dev.hakeem.nextcash.entity.User;
import com.dev.hakeem.nextcash.web.request.UserCreateDTO;
import com.dev.hakeem.nextcash.web.response.UserCreateResponse;
import org.springframework.stereotype.Component;

@Component
public class ToUserMapper {

    public static User CreateUserTO(UserCreateDTO createDTO){
        User user = new User();

        user.setId(createDTO.getId());
        user.setEmail(createDTO.getEmail());
        user.setUsername(createDTO.getUsername());
        user.setPassword(createDTO.getPassword());
        return user;
    }

    public  UserCreateResponse ResponseUserTO(User user){
        UserCreateResponse createResponse = new UserCreateResponse();

        createResponse.setId(user.getId());
        createResponse.setEmail(user.getEmail());
        createResponse.setUsername(user.getUsername());

        String role = user.getRole().name().substring("ROLE_".length());
        createResponse.setRole(role);

        return createResponse;
    }
}
