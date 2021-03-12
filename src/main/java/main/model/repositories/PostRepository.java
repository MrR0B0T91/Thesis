package main.model.repositories;

import main.model.ModerationStatus;
import main.model.Posts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Posts, Integer> {

  Page<Posts> findAll(ModerationStatus moderationStatus, Pageable pageable);
  Page<Posts> findByIsActive(boolean isActive, Pageable pageable);
  Page<Posts> findByTitle(String tittle, Pageable pageable);
}
