package com.example.enums;

public enum FeedTopic {
    GENERAL("topic.general"),
    NEW_PATIENT("topic.newPatient"),
    NEW_HEART("topic.newHeart"),
    NEW_CANDIDATE("topic.newCandidate");

    private final String messageKey;

    FeedTopic(String messageKey) {
        this.messageKey = messageKey;
    }

    public String getMessageKey() {
        return messageKey;
    }
}