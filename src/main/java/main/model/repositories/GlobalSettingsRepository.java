package main.model.repositories;

import main.model.GlobalSettings;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GlobalSettingsRepository extends CrudRepository<GlobalSettings, Integer> {

  Optional<GlobalSettings> findValueByCode(String code);
}
