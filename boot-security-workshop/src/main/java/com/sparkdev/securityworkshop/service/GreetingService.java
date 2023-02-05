package com.sparkdev.securityworkshop.service;

import org.springframework.stereotype.Service;

@Service
public class GreetingService {

    public String greet(){
        return "Hello World Server";
    }

    public String goodBye(){
        return "Good bye World";
    }
}
