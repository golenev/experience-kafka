package com.experience_kafka.controller;

import com.experience_kafka.config.MyUserDetails;
import com.experience_kafka.model.MyUser;
import com.experience_kafka.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @PostMapping("/add-user")
    public String addUser (@RequestBody MyUser user) {
        myUserDetailsService.addUser(user);
        return "User is saved";
    }

    @GetMapping("/pull-user")
    public UserDetails pullUser (@RequestParam String name) {
       return myUserDetailsService.loadUserByUsername(name);
    }

}
