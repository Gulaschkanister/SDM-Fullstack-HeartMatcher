package com.example.notification;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class NewsletterFeedListener {
    private final NewsletterRepository newsletterRepository;

    public NewsletterFeedListener(NewsletterRepository newsletterRepository) {
        this.newsletterRepository = newsletterRepository;
    }

    @EventListener
    public void handleFeedNotification(FeedNotificationEvent event) {
        newsletterRepository.save(new NewsletterEntry(event.getTopic(), event.getMessage()));
    }
}