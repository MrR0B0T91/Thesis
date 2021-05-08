package main.model.repositories;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import main.model.ModerationStatus;
import main.model.Posts;
import main.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Posts, Integer> {

  List<Posts> findAllByModerationStatus(ModerationStatus moderationStatus);

  @Query(
      "SELECT p "
          + "FROM Posts p "
          + "LEFT JOIN User u ON u.id = p.user "
          + "LEFT JOIN PostVotes pv1 ON p.id = pv1.postId AND pv1.value = 1 "
          + "LEFT JOIN PostVotes pv2 ON p.id = pv2.postId AND pv2.value = -1 "
          + "LEFT JOIN PostComments pc ON p.id = pc.postId "
          + "WHERE p.isActive = 1 AND p.moderationStatus = 'ACCEPTED' AND p.time <= CURRENT_DATE() "
          + "GROUP BY p.id")
  Page<Posts> findAll(Pageable pageable);

  @Query(
      "SELECT p "
          + "FROM Posts p "
          + "LEFT JOIN User u ON u.id = p.user "
          + "LEFT JOIN PostVotes pv1 ON p.id = pv1.postId AND pv1.value = 1 "
          + "LEFT JOIN PostVotes pv2 ON p.id = pv2.postId AND pv2.value = -1 "
          + "LEFT JOIN PostComments pc ON p.id = pc.postId "
          + "WHERE p.isActive = 1 AND p.moderationStatus = 'ACCEPTED' AND p.time <= CURRENT_DATE() "
          + "GROUP BY p.id "
          + "ORDER BY COUNT(pv1) DESC")
  Page<Posts> findPostsOrderByLikes(Pageable pageable);

  @Query(
      "SELECT p "
          + "FROM Posts p "
          + "LEFT JOIN User u ON u.id = p.user "
          + "LEFT JOIN PostVotes pv1 ON p.id = pv1.postId AND pv1.value = 1 "
          + "LEFT JOIN PostVotes pv2 ON p.id = pv2.postId AND pv2.value = -1 "
          + "LEFT JOIN PostComments pc ON p.id = pc.postId "
          + "WHERE p.isActive = 1 AND p.moderationStatus = 'ACCEPTED' AND p.time <= CURRENT_DATE() "
          + "GROUP BY p.id "
          + "ORDER BY COUNT(pc) DESC")
  Page<Posts> findPostsOrderByComments(Pageable pageable);

  @Query(
      "SELECT p "
          + "FROM Posts p "
          + "LEFT JOIN User u ON u.id = p.user "
          + "LEFT JOIN PostVotes pv1 ON p.id = pv1.postId AND pv1.value = 1 "
          + "LEFT JOIN PostVotes pv2 ON p.id = pv2.postId AND pv2.value = -1 "
          + "LEFT JOIN PostComments pc ON p.id = pc.postId "
          + "WHERE p.isActive = 1 AND p.moderationStatus = 'ACCEPTED' AND p.time <= CURRENT_DATE() AND upper(p.title) like concat('%', upper(?1), '%') "
          + "GROUP BY p.id")
  Page<Posts> findPostsByQuery(String query, Pageable pageable);

  @Query(
      "SELECT p "
          + "FROM Posts p "
          + "WHERE p.isActive = 1 AND p.moderationStatus = 'ACCEPTED' AND p.time <= CURRENT_DATE() AND p.time <= :year "
          + "GROUP BY p.id")
  List<Posts> findPostsByYear(@Param("year") Calendar year);

  @Query(
      "SELECT p "
          + "FROM Posts p "
          + "LEFT JOIN User u ON u.id = p.user "
          + "LEFT JOIN PostVotes pv1 ON p.id = pv1.postId AND pv1.value = 1 "
          + "LEFT JOIN PostVotes pv2 ON p.id = pv2.postId AND pv2.value = -1 "
          + "LEFT JOIN PostComments pc ON p.id = pc.postId "
          + "LEFT JOIN Tag2Post t2p ON p.id = t2p.postId "
          + "LEFT JOIN Tags t ON t.id = t2p.tagId "
          + "WHERE p.isActive = 1 AND p.moderationStatus = 'ACCEPTED' AND p.time <= CURRENT_DATE() AND p.id = :id "
          + "GROUP BY p.id")
  Optional<Posts> findPostById(@Param("id") int id);

  @Query(
      "SELECT p "
          + "FROM Posts p "
          + "LEFT JOIN User u ON u.id = p.user "
          + "LEFT JOIN PostVotes pv1 ON p.id = pv1.postId AND pv1.value = 1 "
          + "LEFT JOIN PostVotes pv2 ON p.id = pv2.postId AND pv2.value = -1 "
          + "LEFT JOIN PostComments pc ON p.id = pc.postId "
          + "WHERE p.isActive = 1 AND p.moderationStatus = :STATUS AND p.moderatorId = :id "
          + "GROUP BY p.id")
  Page<Posts> findModeratedPosts(@Param("id") int id, @Param("STATUS") ModerationStatus status,
      Pageable pageable);

  @Query(
      "SELECT p "
          + "FROM Posts p "
          + "LEFT JOIN User u ON u.id = p.user "
          + "LEFT JOIN PostVotes pv1 ON p.id = pv1.postId AND pv1.value = 1 "
          + "LEFT JOIN PostVotes pv2 ON p.id = pv2.postId AND pv2.value = -1 "
          + "LEFT JOIN PostComments pc ON p.id = pc.postId "
          + "WHERE p.isActive = 0 AND p.user = :user "
          + "GROUP BY p.id")
  Page<Posts> findInactivePosts(@Param("user") User user, Pageable pageable);

  @Query(
      "SELECT p "
          + "FROM Posts p "
          + "LEFT JOIN User u ON u.id = p.user "
          + "LEFT JOIN PostVotes pv1 ON p.id = pv1.postId AND pv1.value = 1 "
          + "LEFT JOIN PostVotes pv2 ON p.id = pv2.postId AND pv2.value = -1 "
          + "LEFT JOIN PostComments pc ON p.id = pc.postId "
          + "WHERE p.isActive = 1 AND p.user = :user AND p.moderationStatus = 'NEW' "
          + "GROUP BY p.id")
  Page<Posts> findPendingPosts(@Param("user") User user, Pageable pageable);

  @Query(
      "SELECT p "
          + "FROM Posts p "
          + "LEFT JOIN User u ON u.id = p.user "
          + "LEFT JOIN PostVotes pv1 ON p.id = pv1.postId AND pv1.value = 1 "
          + "LEFT JOIN PostVotes pv2 ON p.id = pv2.postId AND pv2.value = -1 "
          + "LEFT JOIN PostComments pc ON p.id = pc.postId "
          + "WHERE p.isActive = 1 AND p.user = :user AND p.moderationStatus = 'DECLINED' "
          + "GROUP BY p.id")
  Page<Posts> findDeclinedPosts(@Param("user") User user, Pageable pageable);

  @Query(
      "SELECT p "
          + "FROM Posts p "
          + "LEFT JOIN User u ON u.id = p.user "
          + "LEFT JOIN PostVotes pv1 ON p.id = pv1.postId AND pv1.value = 1 "
          + "LEFT JOIN PostVotes pv2 ON p.id = pv2.postId AND pv2.value = -1 "
          + "LEFT JOIN PostComments pc ON p.id = pc.postId "
          + "WHERE p.isActive = 1 AND p.user = :user AND p.moderationStatus = 'ACCEPTED' "
          + "GROUP BY p.id")
  Page<Posts> findPublishedPosts(@Param("user") User user, Pageable pageable);

  @Query(
      "SELECT p "
          + "FROM Posts p "
          + "WHERE p.id = :id")
  Posts findById(@Param("id") int id);
}
