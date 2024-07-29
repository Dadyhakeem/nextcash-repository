package com.dev.hakeem.nextcash.jwt;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;



public class JwtUserDetails extends User {

    private com.dev.hakeem.nextcash.entity.User user;
    public JwtUserDetails(com.dev.hakeem.nextcash.entity.User user) {
        super(user.getEmail() ,user.getPassword(), AuthorityUtils.createAuthorityList(user.getRole().name()));
        this.user = user;
    }

    public  Long getId(){
        return this.user.getId();
    }

    public  String getRole(){
        return user.getRole().name();
    }


}
