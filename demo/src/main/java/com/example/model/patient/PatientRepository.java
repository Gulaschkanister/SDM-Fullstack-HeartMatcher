package com.example.model.patient;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.database.PatientEntity;

public interface PatientRepository extends JpaRepository<PatientEntity, Long> {

}
