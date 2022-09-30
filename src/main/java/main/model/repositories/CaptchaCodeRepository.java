package main.model.repositories;

import main.model.CaptchaCodes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CaptchaCodeRepository extends JpaRepository<CaptchaCodes, Integer> {

    @Query("SELECT c "
            + "FROM CaptchaCodes c "
            + "WHERE c.secretCode = :secret")
    CaptchaCodes findBySecret(@Param("secret") String secret);

}
