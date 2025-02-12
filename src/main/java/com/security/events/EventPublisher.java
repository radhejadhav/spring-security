package com.security.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.ZonedDateTime;

@Component
@Slf4j
public class EventPublisher {


    private final ApplicationEventPublisher publisher;

    public EventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void publishEmailUpdateEvent(String email){
        log.info("Publishing email event");
        EmailUpdateEvent event = new EmailUpdateEvent(this, Clock.system(ZonedDateTime.now().getZone()), email);
        publisher.publishEvent(event);
    }

}
