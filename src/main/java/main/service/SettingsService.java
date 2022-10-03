package main.service;

import java.util.List;

import main.api.requset.SettingsRequest;
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

    public SettingsResponse changeSettings(SettingsRequest settingsRequest) {
        SettingsResponse response = new SettingsResponse();
        GlobalSettings multiuserMode = globalSettingsRepository.findByCode("MULTIUSER_MODE");
        GlobalSettings postPremoderation = globalSettingsRepository.findByCode("POST_PREMODERATION");
        GlobalSettings statisticsIsPublic = globalSettingsRepository.findByCode("STATISTICS_IS_PUBLIC");

        if (!settingsRequest.isMultiuserMode()) {
            response.setMultiuserMode(false);
            multiuserMode.setValue("NO");
        } else {
            response.setMultiuserMode(true);
            multiuserMode.setValue("YES");
        }
        globalSettingsRepository.save(multiuserMode);

        if (!settingsRequest.isPostPremoderation()) {
            response.setPostPremoderation(false);
            postPremoderation.setValue("NO");
        } else {
            response.setPostPremoderation(true);
            postPremoderation.setValue("YES");
        }
        globalSettingsRepository.save(postPremoderation);

        if (!settingsRequest.isStatisticsIsPublic()) {
            response.setStatisticsIsPublic(false);
            statisticsIsPublic.setValue("NO");
        } else {
            response.setStatisticsIsPublic(true);
            statisticsIsPublic.setValue("YES");
        }
        globalSettingsRepository.save(statisticsIsPublic);

        return response;
    }
}
