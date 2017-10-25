package com.nari.oauth2.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SomeController {

    @GetMapping("/")
    public String toString() {
        return "index";
    }
}
