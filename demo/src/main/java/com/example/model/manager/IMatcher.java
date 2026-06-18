package com.example.model.manager;

import com.example.model.heart.Heart;
import com.example.model.patient.Patient;

public interface IMatcher {
    public boolean match(Patient patient, Heart heart);
}
