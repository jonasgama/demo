package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.dto.LanguageDTO;
import com.example.entity.CountryEntity;
import com.example.entity.LanguageEntity;
import com.example.repo.CountryRepository;
import com.example.repo.LanguagesRepository;
import com.example.service.LangService;
import io.micronaut.core.type.Argument;
import io.micronaut.data.model.Slice;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.rxjava3.http.client.Rx3HttpClient;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.reactivex.rxjava3.core.Flowable;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@MicronautTest
class LanguageV2ControllerTest {

  @Inject
  @Client("/languages/v2")
  private Rx3HttpClient client;

  @Inject
  private LangService langService;


  @BeforeEach
  public void setup(){

    CountryEntity angola = new CountryEntity("angola");
    CountryEntity brazil = new CountryEntity("brazil");
    CountryEntity portugal = new CountryEntity("portugal");

    langService.save(new LanguageEntity(UUID.randomUUID(), "portugues"), List.of(angola, brazil, portugal));
    langService.save(new LanguageEntity(UUID.randomUUID(), "congo"), List.of(angola));
    langService.save(new LanguageEntity(UUID.randomUUID(), "ganguela"), List.of(angola));

  }

  @AfterEach
  public void after(){

    langService.clean();

  }

  @Test
  public void shouldGetAllInsertedLanguages() {
    Flowable<HttpResponse<List<LanguageDTO>>> exchange = client.exchange(HttpRequest.GET("/"), Argument.listOf(LanguageDTO.class));
    List<LanguageDTO> body = exchange.blockingLast().body();
    assertTrue(body.size()==3);
    assertEquals(exchange.blockingLast().getStatus(), HttpStatus.OK);
  }

  @Test
  public void shouldGetAllByDescOrder() {
    Flowable<HttpResponse<List<LanguageDTO>>> exchange = client.exchange(HttpRequest.GET("/desc"), Argument.listOf(LanguageDTO.class));
    List<LanguageDTO> body = exchange.blockingLast().body();
    assertTrue(body.size()==3);

    assertEquals(body.get(0).language, "portugues");
    assertTrue(body.get(0).getCountries().containsAll(List.of("angola", "brazil", "portugal")));
    assertTrue(body.get(0).getCountries().size() == 3);

    assertEquals(body.get(1).language, "ganguela");
    assertTrue(body.get(1).getCountries().contains("angola"));
    assertTrue(body.get(1).getCountries().size() == 1);

    assertEquals(body.get(2).language, "congo");
    assertTrue(body.get(2).getCountries().contains("angola"));
    assertTrue(body.get(2).getCountries().size() == 1);
    assertEquals(exchange.blockingLast().getStatus(), HttpStatus.OK);
  }

  @Test
  public void shouldFindByName() {
    Flowable<HttpResponse<List<LanguageDTO>>> exchange = client.exchange(HttpRequest.GET("/?name=gang"), Argument.listOf(LanguageDTO.class));
    List<LanguageDTO> body = exchange.blockingLast().body();
    assertTrue(body.size()>=0);

    assertEquals(body.get(0).language, "ganguela");
    assertTrue(body.get(0).getCountries().contains("angola"));
    assertTrue(body.get(0).getCountries().size() == 1);

  }

  @Test
  public void shouldPaginateByNumberAndSize() {
    Flowable<HttpResponse<Slice>> exchange = client.exchange(HttpRequest.GET("/pagination?page=1&size=2"), Slice.class);
    Slice<LanguageDTO> body = exchange.blockingLast().body();
    assertTrue(body.getContent().size()>=2);
    assertTrue(body.getPageNumber()==0);
    assertTrue(body.getSize()==2);
    assertTrue(body.getNumberOfElements()==2);
  }
}
