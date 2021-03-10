package main.service;

import main.api.response.SettingsResponse;
import main.model.repositories.GlobalSettingsRepository;
import org.springframework.stereotype.Service;

@Service
public class SettingsService {

  private GlobalSettingsRepository globalSettingsRepository;

  public SettingsService(GlobalSettingsRepository globalSettingsRepository) {
    this.globalSettingsRepository = globalSettingsRepository;
  }

  public SettingsResponse getGlobalSettings(){
    SettingsResponse settingsResponse = new SettingsResponse();
    settingsResponse.setMultiuserMode(true);
    return settingsResponse;
  }
}
