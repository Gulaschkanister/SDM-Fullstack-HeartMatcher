package com.example;

public enum FeedTopic {
    GENERAL("Allgemein"),
    NEW_PATIENT("Neuer Patient"),
    NEW_HEART("Neues Herz"),
    NEW_CANDIDATE("Neuer Kandidat");

    private final String label;

    FeedTopic(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}