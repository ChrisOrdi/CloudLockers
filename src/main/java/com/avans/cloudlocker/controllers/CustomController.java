package com.avans.cloudlocker.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomController {
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String custom() {
        return "Hello, world!";
    }
}
