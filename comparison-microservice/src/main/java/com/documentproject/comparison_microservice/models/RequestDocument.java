package com.documentproject.comparison_microservice.models;

public class RequestDocument {
    private String documentId1;
    private String documentId2;

    public String getDocumentId1() {
        return documentId1;
    }

    public void setDocumentId1(String documentId1) {
        this.documentId1 = documentId1;
    }

    public String getDocumentId2() {
        return documentId2;
    }

    public void setDocumentId2(String documentId2) {
        this.documentId2 = documentId2;
    }
}
