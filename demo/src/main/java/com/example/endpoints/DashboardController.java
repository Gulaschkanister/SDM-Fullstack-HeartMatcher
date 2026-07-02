package com.example.endpoints;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.database.HeartEntity;
import com.example.database.PatientEntity;
import com.example.matcher.KomplexMatcher;
import com.example.model.heart.Heart;
import com.example.model.heart.HeartRepository;
import com.example.model.patient.Patient;
import com.example.model.patient.PatientRepository;

import java.util.List;

@Controller
public class DashboardController {
    private static final String PAGE_VIEW = "dashboard";

    private final PatientRepository patientRepository;
    private final HeartRepository heartRepository;

    public DashboardController(PatientRepository patientRepository, HeartRepository heartRepository) {
        this.patientRepository = patientRepository;
        this.heartRepository = heartRepository;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<PatientEntity> patients = patientRepository.findAll();
        List<HeartEntity> hearts = heartRepository.findAll();

        model.addAttribute("patientCount", patients.size());
        model.addAttribute("heartCount", hearts.size());
        model.addAttribute("candidateCount", countCandidates(patients, hearts));
        return PAGE_VIEW;
    }

    private int countCandidates(List<PatientEntity> patients, List<HeartEntity> hearts) {
        KomplexMatcher matcher = new KomplexMatcher();
        int candidateCount = 0;

        for (PatientEntity patientEntity : patients) {
            Patient patient = new Patient(String.valueOf(patientEntity.getId()), patientEntity.getName(), patientEntity.getBloodType());
            for (HeartEntity heartEntity : hearts) {
                Heart heart = new Heart(String.valueOf(heartEntity.getId()), heartEntity.getDonorName(), heartEntity.getBloodType());
                if (matcher.match(patient, heart)) {
                    candidateCount++;
                }
            }
        }

        return candidateCount;
    }
}
