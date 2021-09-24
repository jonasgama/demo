package com.example.auth;

import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
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
      String identity = authUser.check(authenticationRequest);
      if (identity.isBlank()) {
        emitter.onError(AuthenticationResponse.exception());
      } else {
        emitter.onNext(AuthenticationResponse.success(identity));
        emitter.onComplete();
      }
    }, BackpressureStrategy.ERROR);
  }
}
