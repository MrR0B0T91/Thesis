package main.model.repositories;

import main.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {

  @Query(
      "SELECT u "
          + "FROM Users u "
          + "WHERE u.name = :name"
  )
  Users findByName(@Param("name") String name);
}
