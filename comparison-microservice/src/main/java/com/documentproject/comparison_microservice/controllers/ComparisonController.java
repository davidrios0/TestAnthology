package com.documentproject.comparison_microservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ComparisonController {

    @GetMapping("/hola")
    public static String holaMundo() {

        return "hola mundo";
    }
}
