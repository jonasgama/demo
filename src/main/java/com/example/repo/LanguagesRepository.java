package com.example.repo;

import com.example.entity.LanguageEntity;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import java.util.List;
import java.util.UUID;

@Repository
public interface LanguagesRepository extends CrudRepository<LanguageEntity, UUID> {

  List<LanguageEntity> findAll();

  List<LanguageEntity> listOrderByNameDesc();

  List<LanguageEntity> findByNameLike(String name);

}
