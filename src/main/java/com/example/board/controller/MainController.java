package com.example.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/projects")
    public String projects(){
        System.out.println("Projects");
        return "projects";
    }
}
