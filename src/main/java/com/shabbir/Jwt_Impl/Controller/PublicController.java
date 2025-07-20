package com.shabbir.Jwt_Impl.Controller;

import com.shabbir.Jwt_Impl.Entity.User;
import com.shabbir.Jwt_Impl.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    UserService userService;

    @GetMapping("/health-check")
    public String healthCheck(){
        return "Ok";
    }

    @PostMapping("/admin")
    public ResponseEntity<?> addNewAdmin(@RequestBody User newAdmin){
        return userService.signupAAdmin(newAdmin);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User newUser){
       return userService.signupAUser(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user){
        return userService.login(user);
    }

}
