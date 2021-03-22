package main.service;

import main.api.response.SettingsResponse;
import main.model.GlobalSettings;
import main.model.repositories.GlobalSettingsRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SettingsService {

  private final GlobalSettingsRepository globalSettingsRepository;

  public SettingsService(GlobalSettingsRepository globalSettingsRepository) {
    this.globalSettingsRepository = globalSettingsRepository;
  }

  public SettingsResponse getGlobalSettings() {
    SettingsResponse settingsResponse = new SettingsResponse();

    Optional<GlobalSettings> optionalMultiSettings =
        globalSettingsRepository.findValueByCode("MULTIUSER_MODE");
    GlobalSettings multiSettings = optionalMultiSettings.get();
    String multiValue = multiSettings.getValue();
    if (multiValue.equals("YES")) {
      settingsResponse.setMultiuserMode(true);
    } else {
      settingsResponse.setMultiuserMode(false);
    }

    Optional<GlobalSettings> optionalPostPremodSettings =
        globalSettingsRepository.findValueByCode("POST_PREMODERATION");
    GlobalSettings postPremoderation = optionalPostPremodSettings.get();
    String postPremod = postPremoderation.getValue();
    if (postPremod.equals("YES")) {
      settingsResponse.setPostPremoderation(true);
    } else {
      settingsResponse.setPostPremoderation(false);
    }

    Optional<GlobalSettings> optionalStatisticsSettings =
        globalSettingsRepository.findValueByCode("STATISTICS_IS_PUBLIC");
    GlobalSettings statistics = optionalStatisticsSettings.get();
    String statistic = statistics.getValue();
    if (statistic.equals("YES")) {
      settingsResponse.setStatisticsIsPublic(true);
    } else {
      settingsResponse.setStatisticsIsPublic(false);
    }
    return settingsResponse;
  }
}
