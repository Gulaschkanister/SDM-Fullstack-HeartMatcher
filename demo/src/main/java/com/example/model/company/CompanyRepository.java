package com.example.model.company;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.database.CompanyEntity;

public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {
}
