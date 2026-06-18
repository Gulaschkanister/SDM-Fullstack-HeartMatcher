package com.example;

public class SimpleMatcher implements IMatcher {
    public boolean match(Patient patient, Heart heart) {
        return patient.getBloodType() != null && patient.getBloodType().equals(heart.getBloodType());
    }
}
