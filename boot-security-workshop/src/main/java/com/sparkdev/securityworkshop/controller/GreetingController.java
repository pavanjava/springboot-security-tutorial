package com.sparkdev.securityworkshop.controller;

import com.sparkdev.securityworkshop.service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
public class GreetingController {

    private final GreetingService greetingService;

    public GreetingController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @GetMapping("/greet")
    public ResponseEntity<String> greeting(){
        return ResponseEntity.ok(greetingService.greet());
    }

    @GetMapping("/goodbye")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> sayGoodbye(){
        return ResponseEntity.ok(greetingService.goodBye());
    }
}
