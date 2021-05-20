package main.model.repositories;

import java.util.Optional;
import main.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

  User findByEmail(String email);

  @Query("SELECT u "
      + "FROM User u "
      + "WHERE u.email = :email")
  Optional<User> findUserByEmail(@Param("email") String email);

  User findByCode(String code);
}
