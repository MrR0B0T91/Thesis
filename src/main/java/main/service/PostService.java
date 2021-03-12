package main.service;

import java.util.ArrayList;
import java.util.List;
import main.api.response.PostResponse;
import main.model.ModerationStatus;
import main.model.Posts;
import main.model.repositories.PostCommentRepository;
import main.model.repositories.PostRepository;
import main.model.repositories.PostVoteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PostService {

  private final PostRepository postRepository;
  private final PostVoteRepository postVoteRepository;
  private final PostCommentRepository postCommentRepository;
  private Sort sort;

  public PostService(PostRepository postRepository,
      PostVoteRepository postVoteRepository,
      PostCommentRepository postCommentRepository) {
    this.postRepository = postRepository;
    this.postVoteRepository = postVoteRepository;
    this.postCommentRepository = postCommentRepository;
  }

  public PostResponse getPosts(int offset, int limit, String mode) {

    if (mode.equals("recent")) {
      sort = Sort.by("time").descending();
    }
    if (mode.equals("popular")){
      sort = Sort.by("post_comments").descending();
    }
    if (mode.equals("best")){
      sort = Sort.by("post_votes").descending();
    }
    if (mode.equals("early")){
      sort = Sort.by("time").ascending();
    }

    List<Posts> postsList = new ArrayList<>();

    Pageable pagingAndSort = PageRequest.of(offset, limit, sort.descending());

    Page<Posts> pagePosts = postRepository.findAll(ModerationStatus.ACCEPTED, pagingAndSort);

    postsList = pagePosts.getContent();

    PostResponse postResponse = new PostResponse();

    postResponse.setCount(postsList.size());
    postResponse.setPosts(postsList);

    return postResponse;
  }
}
