package main.model.repositories;

import java.util.List;
import main.model.ModerationStatus;
import main.model.Posts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends PagingAndSortingRepository<Posts, Integer> {

  Page<Posts> findByModerationStatus(ModerationStatus moderationStatus, Pageable pageable);

  Page<Posts> findAll(Pageable pageable);
}
