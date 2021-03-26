package main.service;

import main.api.response.PostResponse;
import main.converter.PostConverter;
import main.dto.PostDto;
import main.model.Posts;
import main.model.repositories.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

  private final PostRepository postRepository;
  private Sort sort;
  private final PostConverter postConverter;

  private List<Posts> postsList;
  private List<PostDto> postDtoList;

  private PostResponse postResponse = new PostResponse();

  public PostService(PostRepository postRepository, PostConverter postConverter) {
    this.postRepository = postRepository;

    this.postConverter = postConverter;
  }

  public PostResponse getPosts(int offset, int limit, String mode) {

    if (mode.equals("recent")) {
      sort = Sort.by("time").descending();
      Pageable pagingRecent = PageRequest.of(offset, limit, sort);
      Page<Posts> recentPage = postRepository.findAll(pagingRecent);
      postsList = recentPage.getContent();
    }
    if (mode.equals("popular")) {
      sort = JpaSort.unsafe(Sort.Direction.DESC,"COUNT(pv1)");
      Pageable pagingPopular = PageRequest.of(offset, limit, sort);
      Page<Posts> popularPage = postRepository.findAll(pagingPopular);
      postsList = popularPage.getContent();
    }
    if (mode.equals("best")) {
      sort = JpaSort.unsafe(Sort.Direction.ASC,"COUNT(pc)");
      Pageable pagingBest = PageRequest.of(offset, limit, sort);
      Page<Posts> bestPage = postRepository.findAll(pagingBest);
      postsList = bestPage.getContent();
    }
    if (mode.equals("early")) {
      sort = Sort.by("time").ascending();
      Pageable pagingEarly = PageRequest.of(offset, limit, sort);
      Page<Posts> earlyPage = postRepository.findAll(pagingEarly);
      postsList = earlyPage.getContent();
    }

    postResponse.setCount(postsList.size());
    postResponse.setPosts(postsList);

    return postResponse;
  }
}
