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
      "SELECT p "
          + "FROM Posts p "
          + "LEFT JOIN PostVotes pv1 ON p.id = pv1.postId AND pv1.value = 1 "
          + "WHERE p.isActive = 1 AND p.moderationStatus = 'ACCEPTED' AND p.time <= CURRENT_DATE() "
          + "GROUP BY p.id "
          + "ORDER BY COUNT(pv1) DESC")
  Page<Posts> findPostsOrderByLikes(Pageable pageable);

  @Query(
      "SELECT p "
          + "FROM Posts p "
          + "LEFT JOIN PostComments pc ON p.id = pc.postId "
          + "GROUP BY p.id "
          + "ORDER BY COUNT(pc) ASC")
  Page<Posts> findPostsOrderByComments(Pageable pageable);
}
