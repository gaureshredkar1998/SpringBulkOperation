package com.asset.bulkOperations.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class helloWorld {

    @GetMapping("/t")
    public String helloWorld() {
        return "Hello World!";
    }
}
