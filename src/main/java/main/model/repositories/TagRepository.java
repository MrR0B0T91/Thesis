package main.model.repositories;

import java.util.List;
import main.model.Tags;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends CrudRepository<Tags, Integer> {

  @Query(
      "SELECT t "
          + "FROM Tags t "
          + "WHERE upper(t.name) like concat('%', upper(?1), '%') "
          + "GROUP BY t.id")
  List<Tags> findByName(String name);

  List<Tags> findAll();
}
