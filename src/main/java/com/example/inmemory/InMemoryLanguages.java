package com.example.inmemory;

import com.example.dto.LanguageDTO;
import jakarta.inject.Singleton;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Singleton
public class InMemoryLanguages {

  private Map<UUID, String> additionalLanguages;

  public InMemoryLanguages(){
    additionalLanguages = new HashMap<>();
  }

  public void put(UUID uuid, String language){
    additionalLanguages.put(uuid, language);
  }

  public List<LanguageDTO> get(){
    return additionalLanguages.entrySet().stream()
        .map(uuidStringEntry -> new LanguageDTO(uuidStringEntry.getValue())).collect(Collectors.toList());
  }

  public LanguageDTO get(UUID uuid){
    return new LanguageDTO(additionalLanguages.getOrDefault(uuid, ""));
  }

  public boolean delete(UUID uuid){
    if(additionalLanguages.containsKey(uuid)){
      additionalLanguages.remove(uuid);
      return true;
    }
    return false;
  }


}
