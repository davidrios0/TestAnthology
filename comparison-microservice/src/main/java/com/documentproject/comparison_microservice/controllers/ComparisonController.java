package com.documentproject.comparison_microservice.controllers;

import com.documentproject.comparison_microservice.models.RequestDocument;
import com.documentproject.comparison_microservice.services.RequestDataApi;
import com.documentproject.comparison_microservice.services.TextProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ComparisonController {

    @Autowired
    private RequestDataApi requestAPI;

    @PostMapping("/documents/compare")
    public ResponseEntity<Map<String, Object>> getSimilarityAnalysis(@RequestBody RequestDocument request) {
        try {
            String text_1 = requestAPI.getRequest("documents/" + request.getDocumentId1());
            String text_2 = requestAPI.getRequest("documents/" + request.getDocumentId2());
            if (!(text_1.isEmpty() || text_2.isEmpty())) {
                TextProcessor processor = new TextProcessor();
                Map<String, Object> resultAnalysis = processor.getSimilarity(text_1, text_2);
                return new ResponseEntity<>(resultAnalysis, HttpStatus.OK);
            } else {
                Map<String, Object> resultError = new HashMap<>();
                resultError.put("Error", "Error proccesing documents.");
                return new ResponseEntity<>(resultError, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            Map<String, Object> resultError = new HashMap<>();
            resultError.put("Error", "Error proccesing documents: " + e.getMessage());
            return new ResponseEntity<>(resultError, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
