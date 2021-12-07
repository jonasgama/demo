package com.example.service;

import com.example.dto.LanguageDTO;
import com.example.entity.CountryEntity;
import com.example.entity.LanguageEntity;
import com.example.repo.CountryRepository;
import com.example.repo.LanguagesRepository;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.Slice;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class LangService {

  private static final Logger LOG = LoggerFactory.getLogger(LangService.class);

  @Inject
  private LanguagesRepository langRepo;

  @Inject
  private CountryRepository countryRepo;

  @Transactional(TxType.REQUIRED)
  public List<LanguageDTO> get(){
    return langRepo.findAll().stream().map(
        language -> {
          return getLanguageDTO(language);
        }
    ).collect(Collectors.toList());
  }

  @Transactional(TxType.REQUIRED)
  public List<LanguageDTO> get(String name){
    return langRepo.findByNameLike(name).stream().map(
        language -> {
          return getLanguageDTO(language);
        }
    ).collect(Collectors.toList());
  }

  @Transactional(TxType.REQUIRED)
  public Slice<LanguageDTO> get(Pageable pageable){
    return langRepo.list(pageable).map(
        language -> {
          return getLanguageDTO(language);
        }
    );
  }

  private LanguageDTO getLanguageDTO(LanguageEntity language) {
    LanguageDTO languageDTO = new LanguageDTO(language.getName());
    languageDTO.addCountries(language.getSpokenCountries());

    return languageDTO;
  }

  @Transactional(TxType.REQUIRED)
  public List<LanguageDTO> getByDesc(){
    return langRepo.listOrderByNameDesc().stream().map(
        language -> {
          return getLanguageDTO(language);
        }
    ).collect(Collectors.toList());
  }

  @Transactional(TxType.REQUIRED)
  public void save(LanguageEntity language, List<CountryEntity> countries){
    language.setSpokenCountries(countries);

    langRepo.save(language);
    countries.forEach(
             countryEntity -> countryEntity.addUsedLanguages(Arrays.asList(language))
    );
    countryRepo.saveAll(countries);
  }


  @Transactional(TxType.REQUIRED)
  public void clean(){
    langRepo.deleteAll();
    countryRepo.deleteAll();
  }

}
