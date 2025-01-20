package com.documentproject.management_microservice.controller;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
public class DocumentController {

    @GetMapping("/documents/{fileName}")
    public ResponseEntity<String> getText(@PathVariable String fileName) {

        File file = new File("..\\test-documents\\" + fileName);

        if (!file.exists()) {
            return new ResponseEntity<>("File not founded", HttpStatus.NOT_FOUND);
        }

        String fileExtension = getFileExtension(fileName);

        try {
            if (fileExtension.equals("pdf")) {
                return getTextFromPDF(file);
            } else if (fileExtension.equals("txt")) {
                return getTextFromTXT(file);
            } else {
                return new ResponseEntity<>("Unsupported file format", HttpStatus.BAD_REQUEST);
            }
        } catch (IOException e) {
            return new ResponseEntity<>("Error processing document: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    private ResponseEntity<String> getTextFromPDF(File pdfFile) throws IOException {
        try (PDDocument document = PDDocument.load(pdfFile)) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);
            return new ResponseEntity<>(text, HttpStatus.OK);
        }
    }


    private ResponseEntity<String> getTextFromTXT(File txtFile) throws IOException {
        String text = new String(Files.readAllBytes(txtFile.toPath()));
        return new ResponseEntity<>(text, HttpStatus.OK);
    }

    private String getFileExtension(String fileName) {
        int lastIndexOfDot = fileName.lastIndexOf('.');
        if (lastIndexOfDot == -1) {
            return "";
        }
        return fileName.substring(lastIndexOfDot + 1).toLowerCase();
    }
}
