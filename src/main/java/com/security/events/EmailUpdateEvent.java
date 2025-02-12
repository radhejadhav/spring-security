package com.security.events;

import org.springframework.context.ApplicationEvent;

import java.time.Clock;

public class EmailUpdateEvent extends ApplicationEvent {
    private String email;
    public EmailUpdateEvent(Object source, Clock clock, String email) {
        super(source, clock);
        this.email=email;
    }

    public String getEmail() {
        return email;
    }
}
