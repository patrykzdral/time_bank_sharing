package com.pwr.patrykzdral.timebank.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingsRestController {

    @RequestMapping("/")
    public String greeting(){
        return "Witaj!";
    }
}
