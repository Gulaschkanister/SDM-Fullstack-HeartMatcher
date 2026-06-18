package com.example;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.context.ApplicationEventPublisher;

@Controller
public class PatientController {
    private final PatientRepository patientRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final CandidateNotificationService candidateNotificationService;

    public PatientController(PatientRepository patientRepository,
                             ApplicationEventPublisher eventPublisher,
                             CandidateNotificationService candidateNotificationService) {
        this.patientRepository = patientRepository;
        this.eventPublisher = eventPublisher;
        this.candidateNotificationService = candidateNotificationService;
    }

    @GetMapping("/patients")
    public String list(Model model) {
        model.addAttribute("patients", patientRepository.findAll());
        model.addAttribute("patient", new PatientEntity());
        return "patients";
    }

    @PostMapping("/patients")
    public String create(@ModelAttribute PatientEntity patient) {
        PatientEntity savedPatient = patientRepository.save(patient);
        eventPublisher.publishEvent(new FeedNotificationEvent(
                FeedTopic.NEW_PATIENT,
                String.format("Neuer Patient registriert: %s (%s)",
                        savedPatient.getName() == null ? "unbekannt" : savedPatient.getName(),
                        savedPatient.getBloodType() == null ? "?" : savedPatient.getBloodType())
        ));
        candidateNotificationService.notifyForPatient(savedPatient);
        return "redirect:/patients";
    }
}
