package com.experience_kafka.controller;

import com.experience_kafka.model.MyUser;
import com.experience_kafka.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @PostMapping("/add-user")
    public String addUser (@RequestBody MyUser user) {
        myUserDetailsService.addUser(user);
        return "User is saved";
    }

}
