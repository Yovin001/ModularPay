package com.yovin.tnotificationservice.exception;

public class NotificationNotFoundException extends RuntimeException {
    
    public NotificationNotFoundException(String message) {
        super(message);
    }
}