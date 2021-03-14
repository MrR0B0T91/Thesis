package main.model.repositories;

import java.util.List;
import main.model.Tags;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends CrudRepository<Tags, Integer> {

  List<Tags> findAllByName(String name);

  List<Tags> findAll();
}
