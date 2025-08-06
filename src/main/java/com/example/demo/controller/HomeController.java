package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/")
    public Map<String, Object> home() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "🚀 Spring Boot API está funcionando!");
        response.put("timestamp", LocalDateTime.now());
        response.put("status", "success");
        response.put("version", "1.0.0");
        return response;
    }

    @GetMapping("/hello")
    public Map<String, Object> hello() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Olá! Esta é uma API de teste simples.");
        response.put("timestamp", LocalDateTime.now());
        response.put("endpoints", new String[]{
            "/ - Página inicial",
            "/hello - Mensagem de saudação",
            "/hello/{name} - Saudação personalizada",
            "/api/info - Informações da API",
            "/actuator/health - Health check"
        });
        return response;
    }

    @GetMapping("/hello/{name}")
    public Map<String, Object> helloName(@PathVariable String name) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Olá, " + name + "! 👋");
        response.put("timestamp", LocalDateTime.now());
        response.put("personalized", true);
        return response;
    }

    @GetMapping("/api/info")
    public Map<String, Object> apiInfo() {
        Map<String, Object> response = new HashMap<>();
        response.put("name", "Spring Boot Test API");
        response.put("description", "API simples para testes no Coolify");
        response.put("version", "1.0.0");
        response.put("java_version", System.getProperty("java.version"));
        response.put("spring_version", "3.5.4");
        response.put("timestamp", LocalDateTime.now());
        
        Map<String, String> contact = new HashMap<>();
        contact.put("developer", "Test Developer");
        contact.put("environment", "Production");
        response.put("contact", contact);
        
        return response;
    }
}
