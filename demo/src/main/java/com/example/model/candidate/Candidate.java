package com.example.model.candidate;

import com.example.model.heart.Heart;
import com.example.model.patient.Patient;

public class Candidate implements Icandidate{
    private final Patient patient;
    private final Heart heart;

    public Candidate(Patient patient, Heart heart) {
        this.patient = patient;
        this.heart = heart;
    }

    public Patient getPatient() {
        return patient;
    }

    public Heart getHeart() {
        return heart;
    }

    @Override
    public String toString() {
        return "Candidate{" +
                "patient=" + patient +
                ", heart=" + heart +
                '}';
    }
}
