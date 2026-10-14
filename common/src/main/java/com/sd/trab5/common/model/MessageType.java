package com.sd.trab5.common.model;

public enum MessageType {
    REVIEW("review.product"),
    SUPPORT("support.request");

    private final String defaultRoutingKey;

    MessageType(String defaultRoutingKey) {
        this.defaultRoutingKey = defaultRoutingKey;
    }

    public String getDefaultRoutingKey() {
        return defaultRoutingKey;
    }
}
