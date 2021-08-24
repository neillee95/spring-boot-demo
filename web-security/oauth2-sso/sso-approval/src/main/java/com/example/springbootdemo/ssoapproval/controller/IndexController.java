package com.example.springbootdemo.ssoapproval.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @GetMapping
    public String index() {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return "Approval System, Welcome " + user + ".";
    }

}
