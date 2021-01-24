package com.project.ece651.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"", "/index", "/index.html"})
public class IndexController {

    @GetMapping("/status/check")
    public String status() {
        return "index";
    }
}
