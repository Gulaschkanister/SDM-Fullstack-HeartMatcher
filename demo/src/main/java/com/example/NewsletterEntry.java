package com.example;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.time.LocalDateTime;

@Entity
public class NewsletterEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private FeedTopic topic;
    private String message;
    private LocalDateTime createdAt;

    public NewsletterEntry() {}

    public NewsletterEntry(String message) {
        this(FeedTopic.GENERAL, message);
    }

    public NewsletterEntry(FeedTopic topic, String message) {
        this.topic = topic;
        this.message = message;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public FeedTopic getTopic() { return topic == null ? FeedTopic.GENERAL : topic; }
    public void setTopic(FeedTopic topic) { this.topic = topic; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
