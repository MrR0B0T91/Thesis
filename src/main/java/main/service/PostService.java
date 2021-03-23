package main.service;

import main.api.response.PostResponse;
import main.model.Posts;
import main.model.repositories.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

  private final PostRepository postRepository;

  private List<Posts> postsList;
  private PostResponse postResponse = new PostResponse();

  public PostService(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  public PostResponse getPosts(int offset, int limit, String mode) {

    if (mode.equals("recent")) {
      Pageable pagingRecent = PageRequest.of(offset, limit);
      Page<Posts> recentPage = postRepository.findPostsOrderByRecent(pagingRecent);
      postsList = recentPage.getContent();
    }
    if (mode.equals("popular")) {
      Pageable pagingPopular = PageRequest.of(offset, limit);
      Page<Posts> popularPage = postRepository.findPostsOrderByLikes(pagingPopular);
      postsList = popularPage.getContent();
    }
    if (mode.equals("best")) {
      Pageable pagingBest = PageRequest.of(offset, limit);
      Page<Posts> bestPage = postRepository.findPostsOrderByComments(pagingBest);
      postsList = bestPage.getContent();
    }
    if (mode.equals("early")) {
      Pageable pagingEarly = PageRequest.of(offset, limit);
      Page<Posts> earlyPage = postRepository.findPostsOrderByEarly(pagingEarly);
      postsList = earlyPage.getContent();
    }

    postResponse.setCount(postsList.size());
    postResponse.setPosts(postsList);

    return postResponse;
  }
}
