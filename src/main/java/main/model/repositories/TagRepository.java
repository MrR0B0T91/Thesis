package main.model.repositories;

import java.util.List;
import java.util.Optional;
import main.model.Tags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tags, Integer> {

  @Query(
      "SELECT t "
          + "FROM Tags t "
          + "WHERE upper(t.name) like concat('%', upper(?1), '%') "
          + "GROUP BY t.id")
  List<Tags> findByName(String name);

  @Query(
      "SELECT t "
          + "FROM Tags t "
          + "WHERE upper(t.name) like concat('%', upper(:name), '%') "
          + "GROUP BY t.id")
  Optional<Tags> findTagByName(@Param("name") String name);

  List<Tags> findAll();
}
