package com.example.database;

import jakarta.persistence.Entity;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;

import java.util.LinkedHashSet;
import java.util.Set;

import com.example.enums.FeedTopic;

@Entity
public class CompanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String email;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "company_feed_subscriptions", joinColumns = @JoinColumn(name = "company_id"))
    @Column(name = "feed_topic")
    @Enumerated(EnumType.STRING)
    private Set<FeedTopic> subscriptions = new LinkedHashSet<>();

    public CompanyEntity() {}

    public CompanyEntity(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Set<FeedTopic> getSubscriptions() { return subscriptions; }
    public void setSubscriptions(Set<FeedTopic> subscriptions) { this.subscriptions = subscriptions; }

    public void subscribe(FeedTopic topic) {
        subscriptions.add(topic);
    }

    public void unsubscribe(FeedTopic topic) {
        subscriptions.remove(topic);
    }
}
