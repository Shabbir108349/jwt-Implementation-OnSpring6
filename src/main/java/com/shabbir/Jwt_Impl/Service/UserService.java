package com.shabbir.Jwt_Impl.Service;

import com.shabbir.Jwt_Impl.Entity.User;
import com.shabbir.Jwt_Impl.Repository.UserRepository;
import com.shabbir.Jwt_Impl.Entity.User;
import com.shabbir.Jwt_Impl.Repository.UserRepository;
import com.shabbir.Jwt_Impl.Utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class UserService {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserDetailsServiceImpl userDetailsService;
    public ResponseEntity<?> signupAUser(User newUser){
        try{
            newUser.setPassword(new BCryptPasswordEncoder().encode(newUser.getPassword()));
            newUser.setRole(List.of("USER"));
            userRepository.save(newUser);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e){
            log.error(e.toString());
        }
       return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> signupAAdmin(User newAdmin){
        try {
            newAdmin.setPassword(new BCryptPasswordEncoder().encode(newAdmin.getPassword()));
            newAdmin.setRole(List.of("USER", "ADMIN"));
            userRepository.save(newAdmin);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch(Exception e){
            log.error(e.toString());
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> login(User user){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
            userDetailsService.loadUserByUsername(user.getUsername());
            String token = jwtUtils.generateToke(user.getUsername());
            return new ResponseEntity<>(token,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.toString(),HttpStatus.OK);
        }

    }

    public ResponseEntity<Boolean> updateUser(User updateUser,String username){
        User userInDb = userRepository.findByUsername(username);
        if(userInDb != null){
            userInDb.setUsername(updateUser.getUsername() != null && updateUser.getUsername() != "" ? updateUser.getUsername() : userInDb.getUsername());
            userInDb.setPassword(updateUser.getPassword() != null && updateUser.getPassword() != ""? updateUser.getPassword() : userInDb.getPassword());
            userRepository.save(userInDb);
            return new ResponseEntity<>(true,HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(false,HttpStatus.BAD_REQUEST);
    }
    public ResponseEntity<Boolean> deleteUser(String username){
        User userInDb = userRepository.findByUsername(username);
        if(userInDb != null){
            userRepository.deleteById(userInDb.getId());
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false,HttpStatus.BAD_REQUEST);
    }

}
