package com.example.notification;

import com.example.enums.FeedTopic;

public class FeedNotificationEvent {
    private final FeedTopic topic;
    private final String message;

    public FeedNotificationEvent(FeedTopic topic, String message) {
        this.topic = topic;
        this.message = message;
    }

    public FeedTopic getTopic() {
        return topic;
    }

    public String getMessage() {
        return message;
    }
}