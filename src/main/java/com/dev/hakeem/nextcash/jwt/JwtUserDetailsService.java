package com.dev.hakeem.nextcash.jwt;

import com.dev.hakeem.nextcash.entity.User;
import com.dev.hakeem.nextcash.enums.Role;
import com.dev.hakeem.nextcash.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class JwtUserDetailsService implements UserDetailsService {

    private  final UserService service ;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = service.BuscarPorEmail(email);
        return  new JwtUserDetails(user);
    }

    public  jwtToken gettokenAuthenticated(String email){
      Role role = service.BuscarPorRoleByEmail(email);
      return jwtUtils.createToken(email,role.name().substring("ROLE_".length()));
    }
}
