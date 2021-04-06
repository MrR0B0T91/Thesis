package main.service;

import java.util.List;
import main.api.response.SettingsResponse;
import main.model.GlobalSettings;
import main.model.repositories.GlobalSettingsRepository;
import org.springframework.stereotype.Service;

@Service
public class SettingsService {

  private final GlobalSettingsRepository globalSettingsRepository;

  public SettingsService(GlobalSettingsRepository globalSettingsRepository) {
    this.globalSettingsRepository = globalSettingsRepository;
  }

  public SettingsResponse getGlobalSettings() {

    SettingsResponse settingsResponse = new SettingsResponse();

    List<GlobalSettings> allSettings = globalSettingsRepository.findAll();

    allSettings.forEach(s -> {
      if ((s.getCode().equals("MULTIUSER_MODE")) && (s.getValue().equals("YES"))) {
        settingsResponse.setMultiuserMode(true);
      }
    });

    allSettings.forEach(s -> {
      if ((s.getCode().equals("POST_PREMODERATION")) && (s.getValue().equals("YES"))) {
        settingsResponse.setPostPremoderation(true);
      }
    });

    allSettings.forEach(s -> {
      if ((s.getCode().equals("STATISTICS_IS_PUBLIC")) && (s.getValue().equals("YES"))) {
        settingsResponse.setStatisticsIsPublic(true);
      }
    });

    return settingsResponse;
  }
}
