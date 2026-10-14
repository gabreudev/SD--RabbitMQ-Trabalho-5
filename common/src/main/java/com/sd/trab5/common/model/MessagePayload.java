package com.sd.trab5.common.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.Instant;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MessagePayload {

    private String id;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Instant timestamp;

    private MessageType type;
    private String content;
    private boolean simulateError;

    public MessagePayload() {
    }

    public MessagePayload(String id, Instant timestamp, MessageType type, String content, boolean simulateError) {
        this.id = id;
        this.timestamp = timestamp;
        this.type = type;
        this.content = content;
        this.simulateError = simulateError;
    }

    public static MessagePayload create(MessageType type, String content, boolean simulateError) {
        return new MessagePayload(
                UUID.randomUUID().toString(),
                Instant.now(),
                type,
                content,
                simulateError
        );
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isSimulateError() {
        return simulateError;
    }

    public void setSimulateError(boolean simulateError) {
        this.simulateError = simulateError;
    }

    @Override
    public String toString() {
        return "MessagePayload{" +
                "id='" + id + '\'' +
                ", timestamp=" + timestamp +
                ", type=" + type +
                ", content='" + content + '\'' +
                ", simulateError=" + simulateError +
                '}';
    }
}
