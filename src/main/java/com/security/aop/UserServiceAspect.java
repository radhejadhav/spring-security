package com.security.aop;

import com.security.entities.User;
import com.security.events.EmailUpdateEvent;
import com.security.events.EventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
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

    public UserServiceAspect(EventPublisher event) {
        this.event = event;
    }

    @Pointcut("@annotation(EmailUpdateCheck)")
    public void emailUpdatePointcut(){}

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
