package com.documentproject.management_microservice.controller;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

@RestController
public class DocumentController {

    @GetMapping("/documents/{fileName}")
    public static String getText(@PathVariable String fileName) {

        File pdfFile = new File("..\\test-documents\\" + fileName);

        if (!pdfFile.exists()) {
            return "No existe el documento";
        }

        try (PDDocument document = PDDocument.load(pdfFile)) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);
            document.close();
            return text;
        }
        catch (IOException e) {
            return "Hubo un error al procesar el documento: " + e.getMessage();
        }
    }
}
