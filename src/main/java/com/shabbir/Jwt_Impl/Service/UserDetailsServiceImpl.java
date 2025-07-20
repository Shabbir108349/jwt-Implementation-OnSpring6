package com.shabbir.Jwt_Impl.Service;

import com.shabbir.Jwt_Impl.Entity.User;
import com.shabbir.Jwt_Impl.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userInDb = userRepository.findByUsername(username);
        if (userInDb != null) {
            return org.springframework.security.core.userdetails.User.builder()
                    .username(userInDb.getUsername())
                    .password(userInDb.getPassword())
                    .roles(userInDb.getRole().toArray(new String[0]))
                    .build();
        }
        throw new UsernameNotFoundException("Username is not found");
    }
}
