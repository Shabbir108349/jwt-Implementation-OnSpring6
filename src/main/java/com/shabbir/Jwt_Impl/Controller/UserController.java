package com.shabbir.Jwt_Impl.Controller;

import com.shabbir.Jwt_Impl.Entity.User;
import com.shabbir.Jwt_Impl.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PutMapping
    public ResponseEntity<Boolean> updateUser(@RequestBody User updateUser) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userService.updateUser(updateUser, username);
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userService.deleteUser(username);
    }


}
