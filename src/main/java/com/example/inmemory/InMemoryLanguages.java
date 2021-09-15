package com.example.inmemory;

import jakarta.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Singleton
public class InMemoryLanguages {

  private Map<UUID, String> additionalLanguages;

  public InMemoryLanguages(){
    additionalLanguages = new HashMap<>();
  }

  public void put(UUID uuid, String language){
    additionalLanguages.put(uuid, language);
  }

  public boolean delete(UUID uuid){
    if(additionalLanguages.containsKey(uuid)){
      additionalLanguages.remove(uuid);
      return true;
    }
    return false;
  }


}
