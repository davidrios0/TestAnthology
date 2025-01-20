package com.documentproject.comparison_microservice.controllers;

import com.documentproject.comparison_microservice.models.RequestDocument;
import com.documentproject.comparison_microservice.services.RequestDataApi;
import com.documentproject.comparison_microservice.services.TextProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ComparisonController {

    @Autowired
    private RequestDataApi requestAPI;

    @PostMapping("/documents/compare")
    public Map<String, Object> getSimilarityAnalysis(@RequestBody RequestDocument request) {
        System.out.println(request.getDocumentId1());
        System.out.println(request.getDocumentId2());
        String text_1 = requestAPI.getRequest("documents/" + request.getDocumentId1());
        String text_2 = requestAPI.getRequest("documents/" + request.getDocumentId2());
        TextProcessor processor = new TextProcessor();
        return processor.getSimilarity(text_1, text_2);
    }
}
