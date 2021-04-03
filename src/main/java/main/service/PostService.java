package main.service;

import main.api.response.PostResponse;
import main.dto.PostDto;
import main.dto.UserDto;
import main.model.PostComments;
import main.model.PostVotes;
import main.model.Posts;
import main.model.repositories.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

  private final PostRepository postRepository;

  private Sort sort;

  private PostResponse postResponse = new PostResponse();

  public PostService(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  public PostResponse getPosts(int offset, int limit, String mode) {

    if (mode.equals("recent")) {
      sort = Sort.by("time").descending();
      Pageable pagingRecent = PageRequest.of(offset, limit, sort);
      Page<Posts> recentPage = postRepository.findAll(pagingRecent);
      List<Posts> postsList = recentPage.getContent();
      List<PostDto> postDtoList =
          postsList.stream().map(this::entityToDto).collect(Collectors.toList());
      postResponse.setCount(postsList.size());
      postResponse.setPosts(postDtoList);
    }
    if (mode.equals("popular")) {
      Pageable pagingPopular = PageRequest.of(offset, limit);
      Page<Posts> popularPage = postRepository.findPostsOrderByLikes(pagingPopular);
      List<Posts> postsList = popularPage.getContent();
      List<PostDto> postDtoList =
          postsList.stream().map(this::entityToDto).collect(Collectors.toList());
      postResponse.setCount(postsList.size());
      postResponse.setPosts(postDtoList);
    }
    if (mode.equals("best")) {
      Pageable pagingBest = PageRequest.of(offset, limit);
      Page<Posts> bestPage = postRepository.findPostsOrderByComments(pagingBest);
      List<Posts> postsList = bestPage.getContent();
      List<PostDto> postDtoList =
          postsList.stream().map(this::entityToDto).collect(Collectors.toList());
      postResponse.setCount(postsList.size());
      postResponse.setPosts(postDtoList);
    }
    if (mode.equals("early")) {
      sort = Sort.by("time").ascending();
      Pageable pagingEarly = PageRequest.of(offset, limit, sort);
      Page<Posts> earlyPage = postRepository.findAll(pagingEarly);
      List<Posts> postsList = earlyPage.getContent();
      postResponse.setCount(postsList.size());
      List<PostDto> postDtoList =
          postsList.stream().map(this::entityToDto).collect(Collectors.toList());
      postResponse.setPosts(postDtoList);
    }

    return postResponse;
  }

  private PostDto entityToDto(Posts post) {

    PostDto postDto = new PostDto();
    UserDto userDto = new UserDto();

    userDto.setId(post.getUser().getId());
    userDto.setName(post.getUser().getName());

    List<PostComments> postCommentsList = post.getPostCommentsList();
    List<PostVotes> postVotesList = post.getPostVoteList();

    int countLikes = 0;
    int countDislikes = 0;
    for (PostVotes postVote : postVotesList) {
      int value = postVote.getValue();
      if (value == 1) {
        countLikes++;
      }
      if (value == -1) {
        countDislikes++;
      }
    }

    int countComments = postCommentsList.size();

    postDto.setId(post.getId());
    postDto.setTime(post.getTime());
    postDto.setUserDto(userDto);
    postDto.setTitle(post.getTitle());
    postDto.setText(post.getText());
    postDto.setLikeCount(countLikes);
    postDto.setDislikeCount(countDislikes);
    postDto.setCommentCount(countComments);
    postDto.setViewCount(post.getViewCount());

    return postDto;
  }
}
