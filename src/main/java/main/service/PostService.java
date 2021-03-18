package main.service;

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

import java.util.List;

@Service
public class PostService {

  private final PostRepository postRepository;
  private final PostVoteRepository postVoteRepository;
  private final PostCommentRepository postCommentRepository;
  private Sort sort;
  private final boolean isActive = true;
  private List<Posts> postsList;

  public PostService(
      PostRepository postRepository,
      PostVoteRepository postVoteRepository,
      PostCommentRepository postCommentRepository) {
    this.postRepository = postRepository;
    this.postVoteRepository = postVoteRepository;
    this.postCommentRepository = postCommentRepository;
  }

  public PostResponse getPosts(int offset, int limit, String mode) {

    if (mode.equals("recent")) {
      sort = Sort.by("time").descending();
      Pageable pagingAndSort = PageRequest.of(offset, limit, sort);
      Page<Posts> recentPage =
          postRepository.findAllByModerationStatusAndIsActive(
              ModerationStatus.ACCEPTED, isActive, pagingAndSort);
      postsList = recentPage.getContent();
    }
    if (mode.equals("popular")) {
      sort = Sort.by("count").descending();
      Pageable pagingAndSort = PageRequest.of(offset, limit, sort);
      Page<Posts> popularPage =
          postRepository.findAllLikedPosts(ModerationStatus.ACCEPTED, isActive, pagingAndSort);
      postsList = popularPage.getContent();
    }
    if (mode.equals("best")) {
      sort = Sort.by("post_votes").descending();
    }
    if (mode.equals("early")) {
      sort = Sort.by("time").ascending();
      Pageable pagingAndSort = PageRequest.of(offset, limit, sort);
      Page<Posts> earlyPage =
          postRepository.findAllByModerationStatusAndIsActive(
              ModerationStatus.ACCEPTED, isActive, pagingAndSort);
      postsList = earlyPage.getContent();
    }

    PostResponse postResponse = new PostResponse();

    postResponse.setCount(postsList.size());
    postResponse.setPosts(postsList);

    return postResponse;
  }
}
