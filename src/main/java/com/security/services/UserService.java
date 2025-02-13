package com.security.services;

import com.security.aop.EmailUpdateCheck;
import com.security.dao.UserRepository;
import com.security.dto.CreateUser;
import com.security.entities.Authority;
import com.security.entities.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class UserService {

    private final UserRepository repository;
    private final BCryptPasswordEncoder encoder;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public UserService(UserRepository repository, BCryptPasswordEncoder encoder, KafkaTemplate kafkaTemplate) {
        this.repository = repository;
        this.encoder = encoder;
        this.kafkaTemplate = kafkaTemplate;
    }

    @EventListener(ApplicationStartedEvent.class)
    public void createAdminUser(){
        try {
            User user = this.repository.findUserByUsername("radhejadhav");
            if(user==null){
                CreateUser createUser = new CreateUser();
                createUser.setUsername("radhejadhav");
                createUser.setPassword("Test123");
                createUser.setRoles(List.of("ADMIN"));
                createUser.setFullName("Radheshyam");
                this.createUser(createUser);
            }

            kafkaTemplate.send("TOPIC-1", user.toString());
        }catch (Exception e){
            log.error(e.getMessage());
        }

    }

    public User createUser(CreateUser createUser){
        User user = User.builder()
                .fullName(createUser.getFullName())
                .username(createUser.getUsername())
                .password(encoder.encode(createUser.getPassword()))
                .roles(createUser.getRoles())
                .build();

        return this.repository.save(user);
    }

    @EmailUpdateCheck
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public User updateEmail(String email, String userId){

            User user = this.repository.findAllById(List.of(userId)).get(0);
            user.setUsername(email);
            log.info("updated username: {}", email);
            return this.repository.save(user);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public List<User> getAllUser(){
        return this.repository.findAll();
    }
}
