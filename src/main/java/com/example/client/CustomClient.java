package com.example.client;

import com.example.dto.LanguageDTO;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import java.util.List;
import java.util.UUID;

@Client("/")
public interface CustomClient {

  default String bearer(String accessToken){
    return "Bearer "+ accessToken;
  }

  @Post("/login")
  BearerAccessRefreshToken login(@Body UsernamePasswordCredentials credentials);

  @Get("/languages")
  Flowable<List<LanguageDTO>> getAllLanguages(@Header String authorization);

  @Delete("/languages/{uuid}")
  Single<LanguageDTO> deleteLanguage(@Header String authorization, @PathVariable UUID uuid);
}
