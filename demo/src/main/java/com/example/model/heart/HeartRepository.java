package com.example.model.heart;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.database.HeartEntity;

public interface HeartRepository extends JpaRepository<HeartEntity, Long> {

}
