package com.ecommerce.project.dto;

import java.time.LocalDateTime;

public class ErrorResponse {
    public ErrorResponse(String status, String message, LocalDateTime timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }

    public ErrorResponse() {
    }

    private String status;
    private String message;
    private LocalDateTime timestamp;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
