package main.model.repositories;

import main.model.ModerationStatus;
import main.model.Posts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Posts, Integer> {

  List<Posts> findAllByModerationStatus(ModerationStatus moderationStatus);

  Page<Posts> findAllByModerationStatusAndIsActive(
      ModerationStatus moderationStatus, boolean isActive, Pageable pageable);

  @Query(
      value =
          "select count(value) as count from posts join post_votes on posts.id = post_votes.post_id where value = 1 group by posts.id",
      nativeQuery = true)
  Page<Posts> findAllLikedPosts(
      ModerationStatus moderationStatus, boolean isActive, Pageable pageable);
}
