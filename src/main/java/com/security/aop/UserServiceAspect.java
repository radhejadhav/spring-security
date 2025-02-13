package com.security.aop;

import com.security.dto.LoginUser;
import com.security.entities.User;
import com.security.events.EmailUpdateEvent;
import com.security.events.EventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class UserServiceAspect {

    private final EventPublisher event;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public UserServiceAspect(EventPublisher event, KafkaTemplate<String, String> kafkaTemplate) {
        this.event = event;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Pointcut("@annotation(EmailUpdateCheck)")
    public void emailUpdatePointcut(){}

    @Pointcut("@annotation(LoginEvent)")
    public void loginEvent(){}

    @After("loginEvent() && args(input, ..)")
    public void watchLoginEvent(JoinPoint joinPoint, LoginUser input){
        log.info("User logged IN: {}", input.getUsername());
        kafkaTemplate.send("TOPIC-1", input.getUsername());
    }

    @Before("emailUpdatePointcut() && args(username, userId, ..)")
    public void checkEmailUpdateAccess(JoinPoint joinPoint, String username, String userId){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth
                .getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .filter("ADMIN"::equals)
                .findAny().map("ADMIN"::equals).orElse(false);

        User loggedInUser = (User) auth.getPrincipal();


        if(!(isAdmin || loggedInUser.getUsername().equals(username))){
            log.info("User not allowed to perform this action!");
            throw new AccessDeniedException("User not allowed to perform this action!");
        }
        event.publishEmailUpdateEvent(username);
    }
}
