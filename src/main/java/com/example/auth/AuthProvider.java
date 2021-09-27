package com.example.auth;

import com.example.entity.UserEntity;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import org.reactivestreams.Publisher;

@Singleton
public class AuthProvider implements AuthenticationProvider {

  @Inject
  private AuthUser authUser;

  @Override
  public Publisher<AuthenticationResponse> authenticate(
      HttpRequest<?> httpRequest,
      AuthenticationRequest<?, ?> authenticationRequest) {
    return Flowable.create(emitter -> {
      UserEntity identity = authUser.check(authenticationRequest);
      if (identity.getUsername().isBlank()) {
        emitter.onError(AuthenticationResponse.exception());
      } else {
        Collection<String> roles = List.of(identity.getRole());
        emitter.onNext(AuthenticationResponse.success(identity.getUsername(), roles, new HashMap<>()));
        emitter.onComplete();
      }
    }, BackpressureStrategy.ERROR);
  }
}
