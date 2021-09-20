package com.example.auth;

import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class AuthProvider implements AuthenticationProvider {

  private static final Logger LOG = LoggerFactory.getLogger(AuthProvider.class);

  @Override
  public Publisher<AuthenticationResponse> authenticate(
      HttpRequest<?> httpRequest,
      AuthenticationRequest<?, ?> authenticationRequest) {
    return Flowable.create(emitter -> {
      Object identity = authenticationRequest.getIdentity();
      Object secret = authenticationRequest.getSecret();
      LOG.debug("id {}", identity);
      LOG.debug("secret {}", secret);
      if (identity.equals("sherlock") &&
          secret.equals("pleaseChangeThisSecretForANewOne")) {
        emitter.onNext(AuthenticationResponse.success(String.valueOf(identity)));
        emitter.onComplete();
      } else {
        emitter.onError(AuthenticationResponse.exception());
      }
    }, BackpressureStrategy.ERROR);
  }
}
