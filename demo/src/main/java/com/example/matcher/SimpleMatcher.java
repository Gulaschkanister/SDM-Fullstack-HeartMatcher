package com.example.matcher;

import com.example.model.heart.Heart;
import com.example.model.manager.IMatcher;
import com.example.model.patient.Patient;

public class SimpleMatcher implements IMatcher {
    public boolean match(Patient patient, Heart heart) {
        return patient.getBloodType() != null && patient.getBloodType().equals(heart.getBloodType());
    }
}
