package com.example.endpoints;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.example.database.PatientEntity;
import com.example.enums.FeedTopic;
import com.example.model.patient.PatientRepository;
import com.example.notification.FeedNotificationEvent;
import com.example.service.CandidateNotificationService;

@Controller
public class PatientController {
    private static final String PAGE_VIEW = "patients";
    private static final String PAGE_FRAGMENT = "patients :: page-content";

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
        populateModel(model);
        return PAGE_VIEW;
    }

    @PostMapping("/patients")
    public String create(@ModelAttribute PatientEntity patient,
                         @RequestHeader(value = "HX-Request", required = false) String hxRequest,
                         Model model) {
        PatientEntity persistedPatient = patientRepository.save(patient);
        eventPublisher.publishEvent(new FeedNotificationEvent(
                FeedTopic.NEW_PATIENT,
                String.format("Neuer Patient registriert: %s (%s)",
                        persistedPatient.getName() == null ? "unbekannt" : persistedPatient.getName(),
                        persistedPatient.getBloodType() == null ? "?" : persistedPatient.getBloodType())
        ));
        candidateNotificationService.notifyForPatient(persistedPatient);

        if (isHtmxRequest(hxRequest)) {
            populateModel(model);
            return PAGE_FRAGMENT;
        }

        return "redirect:/patients";
    }

    private void populateModel(Model model) {
        model.addAttribute("patients", patientRepository.findAll());
        model.addAttribute("patient", new PatientEntity());
    }

    private boolean isHtmxRequest(String hxRequest) {
        return "true".equalsIgnoreCase(hxRequest);
    }
}
