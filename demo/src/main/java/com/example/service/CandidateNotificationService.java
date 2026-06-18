package com.example.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import com.example.enums.FeedTopic;
import com.example.database.HeartEntity;
import com.example.database.PatientEntity;
import com.example.matcher.KomplexMatcher;
import com.example.model.heart.Heart;
import com.example.model.heart.HeartRepository;
import com.example.model.patient.Patient;
import com.example.model.patient.PatientRepository;
import com.example.notification.FeedNotificationEvent;;

@Service
public class CandidateNotificationService {
    private final HeartRepository heartRepository;
    private final PatientRepository patientRepository;
    private final ApplicationEventPublisher eventPublisher;

    public CandidateNotificationService(HeartRepository heartRepository,
                                        PatientRepository patientRepository,
                                        ApplicationEventPublisher eventPublisher) {
        this.heartRepository = heartRepository;
        this.patientRepository = patientRepository;
        this.eventPublisher = eventPublisher;
    }

    public void notifyForPatient(PatientEntity savedPatient) {
        Patient patient = new Patient(String.valueOf(savedPatient.getId()), savedPatient.getName(), savedPatient.getBloodType());
        KomplexMatcher matcher = new KomplexMatcher();

        for (HeartEntity heartEntity : heartRepository.findAll()) {
            Heart heart = new Heart(String.valueOf(heartEntity.getId()), heartEntity.getDonorName(), heartEntity.getBloodType());
            if (matcher.match(patient, heart)) {
                publishCandidateMessage(savedPatient.getName(), savedPatient.getBloodType(), heartEntity.getDonorName(), heartEntity.getBloodType());
            }
        }
    }

    public void notifyForHeart(HeartEntity savedHeart) {
        Heart heart = new Heart(String.valueOf(savedHeart.getId()), savedHeart.getDonorName(), savedHeart.getBloodType());
        KomplexMatcher matcher = new KomplexMatcher();

        for (PatientEntity patientEntity : patientRepository.findAll()) {
            Patient patient = new Patient(String.valueOf(patientEntity.getId()), patientEntity.getName(), patientEntity.getBloodType());
            if (matcher.match(patient, heart)) {
                publishCandidateMessage(patientEntity.getName(), patientEntity.getBloodType(), savedHeart.getDonorName(), savedHeart.getBloodType());
            }
        }
    }

    private void publishCandidateMessage(String patientName, String patientBloodType, String donorName, String donorBloodType) {
        String message = String.format(
                "Neuer Kandidat: Patient %s (%s) passt zu Herz %s (%s)",
                safeValue(patientName, "unbekannt"),
                safeValue(patientBloodType, "?") ,
                safeValue(donorName, "unbekannt"),
                safeValue(donorBloodType, "?")
        );
        eventPublisher.publishEvent(new FeedNotificationEvent(FeedTopic.NEW_CANDIDATE, message));
    }

    private String safeValue(String value, String fallback) {
        return value == null || value.isBlank() ? fallback : value;
    }
}