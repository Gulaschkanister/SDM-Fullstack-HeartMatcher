package com.example.notification;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsletterRepository extends JpaRepository<NewsletterEntry, Long> {
}
