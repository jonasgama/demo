package com.example.repo;

import com.example.entity.CountryEntity;
import com.example.entity.LanguageEntity;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Repository
public interface CountryRepository extends CrudRepository<CountryEntity, Long> {

  @Override
  List<CountryEntity> findAll();

  @Query("select c from CountryEntity c join fetch c.usedLanguages ul where ul.name = :language")
  List<CountryEntity> findCountriesByLanguage(String language);

}
