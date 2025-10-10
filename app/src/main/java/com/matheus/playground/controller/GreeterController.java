package com.matheus.playground.controller;

import com.matheus.playground.grpc.server.GreeterClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreeterController {

    private final GreeterClient greeterClient;

    @Autowired
    public GreeterController(GreeterClient greeterClient) {
        this.greeterClient = greeterClient;
    }

    @GetMapping("/greet")
    public String greet(@RequestParam String name) {
        return greeterClient.sayHello(name);
    }
}