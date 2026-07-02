package com.example.service;

import java.util.ArrayList;
import java.util.List;

import com.example.matcher.IMatcher;
import com.example.model.candidate.Candidate;
import com.example.model.heart.Heart;
import com.example.model.manager.Manager;
import com.example.model.patient.Patient;

public class MatchingService {
    private final Manager<Patient> patientManager;
    private final Manager<Heart> heartManager;

    public MatchingService(Manager<Patient> patientManager, Manager<Heart> heartManager) {
        this.patientManager = patientManager;
        this.heartManager = heartManager;
    }

    public List<Candidate> findCandidates(IMatcher matcher) {
        List<Candidate> candidates = new ArrayList<>();
        for (Patient p : patientManager.getAllManagedElements()) {
            for (Heart h : heartManager.getAllManagedElements()) {
                if (matcher.match(p, h)) {
                    candidates.add(new Candidate(p, h));
                }
            }
        }
        return candidates;
    }
}
