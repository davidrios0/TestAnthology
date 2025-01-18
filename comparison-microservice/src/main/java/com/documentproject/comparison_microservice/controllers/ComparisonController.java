package com.documentproject.comparison_microservice.controllers;

import com.documentproject.comparison_microservice.services.TextProcessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ComparisonController {

    @GetMapping("/hola")
    public static List<String> holaMundo() {
        String text_1 = "La casa es azul.";
        String text_2 = "La casa es azul.";
        TextProcessor processor = new TextProcessor();
        return processor.getSimilarity(text_1, text_2);
    }
}
