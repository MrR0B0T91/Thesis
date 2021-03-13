package main.model.repositories;

import java.util.List;
import main.model.ModerationStatus;
import main.model.Posts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Posts, Integer> {

  List<Posts> findAllByModerationStatus(ModerationStatus moderationStatus);

  Page<Posts> findAllByModerationStatusAndIsActive(ModerationStatus moderationStatus, boolean isActive, Pageable pageable);

}
