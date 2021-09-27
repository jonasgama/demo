package com.example.auth;

import com.example.entity.UserEntity;
import com.example.repo.UserRepository;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import io.micronaut.security.authentication.AuthenticationRequest;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class AuthUser {

  private static final Logger LOG = LoggerFactory.getLogger(AuthProvider.class);

  @Inject
  private UserRepository userRepository;

  public UserEntity check(AuthenticationRequest<?, ?> authenticationRequest){
    Object identity = authenticationRequest.getIdentity();
    Object secret = authenticationRequest.getSecret();
    LOG.debug("id {}", identity);
    LOG.debug("secret {}", secret);

    return userRepository.findByUsername(String.valueOf(identity))
            .filter(userEntity -> userEntity.getUsername().equals(identity))
                .filter(userEntity -> userEntity.getPassword().equals(secret))
                        .orElse(new UserEntity());
  }


  @EventListener
  public void bulk(StartupEvent e){
    final UserEntity entity = new UserEntity();
    entity.setId(UUID.randomUUID());
    entity.setUsername("sherlock");
    entity.setPassword("pleaseChangeThisSecretForANewOne");
    entity.setRole("ADMIN");
    userRepository.save(entity);
  }

}
