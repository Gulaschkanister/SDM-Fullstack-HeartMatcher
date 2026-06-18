package com.example;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DashboardController {
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

        int candidateCount = 0;
        KomplexMatcher matcher = new KomplexMatcher();
        for (PatientEntity p : patients) {
            for (HeartEntity h : hearts) {
                // adapt entities to simple domain objects used by matcher
                Patient dp = new Patient(String.valueOf(p.getId()), p.getName(), p.getBloodType());
                Heart dh = new Heart(String.valueOf(h.getId()), h.getDonorName(), h.getBloodType());
                if (matcher.match(dp, dh)) candidateCount++;
            }
        }

        model.addAttribute("patientCount", patients.size());
        model.addAttribute("heartCount", hearts.size());
        model.addAttribute("candidateCount", candidateCount);
        return "dashboard";
    }
}
