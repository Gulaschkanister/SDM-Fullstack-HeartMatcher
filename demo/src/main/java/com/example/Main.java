package com.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    CommandLineRunner bootstrapData(PatientRepository patientRepo, HeartRepository heartRepo) {
        return args -> {
            if (patientRepo.count() == 0) {
                patientRepo.save(new PatientEntity("Anna", "A+"));
                patientRepo.save(new PatientEntity("Ben", "O-"));
                patientRepo.save(new PatientEntity("Clara", "B+"));
            }
            if (heartRepo.count() == 0) {
                heartRepo.save(new HeartEntity("Donor1", "A+"));
                heartRepo.save(new HeartEntity("Donor2", "O-"));
                heartRepo.save(new HeartEntity("Donor3", "AB+"));
            }
        };
    }
}