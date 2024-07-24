package com.codefarm.idmaster.model;

import java.time.LocalDateTime;

public class IdWithTimestamp {

    private String id;
    private LocalDateTime timestamp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public IdWithTimestamp(String id, LocalDateTime timestamp) {
        this.id = id;
        this.timestamp = timestamp;
    }
}
