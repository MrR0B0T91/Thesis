package main.model.repositories;

import java.util.Optional;
import main.model.PostVotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostVoteRepository extends JpaRepository<PostVotes, Integer> {

  @Query("SELECT v "
      + "FROM PostVotes v "
      + "WHERE v.postId = :postId AND v.userId = :userId")
  Optional<PostVotes> findByPostId(@Param("postId") int postId, @Param("userId") int userId);

}
