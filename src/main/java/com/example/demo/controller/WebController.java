package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.time.LocalDateTime;

@Controller
public class WebController {

    @GetMapping("/page")
    public String index(Model model) {
        model.addAttribute("title", "Spring Boot Test Page");
        model.addAttribute("message", "🚀 Aplicação funcionando no Coolify!");
        model.addAttribute("timestamp", LocalDateTime.now());
        return "index";
    }
}
