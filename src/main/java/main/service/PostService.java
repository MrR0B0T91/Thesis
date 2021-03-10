package main.service;

import java.util.Date;
import main.api.response.PostResponse;
import main.model.ModerationStatus;
import main.model.Posts;
import main.model.Users;
import main.model.repositories.PostCommentRepository;
import main.model.repositories.PostRepository;
import main.model.repositories.PostVoteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class PostService {

  private final PostRepository postRepository;
  private final PostVoteRepository postVoteRepository;
  private final PostCommentRepository postCommentRepository;

  public PostService(PostRepository postRepository,
      PostVoteRepository postVoteRepository,
      PostCommentRepository postCommentRepository) {
    this.postRepository = postRepository;
    this.postVoteRepository = postVoteRepository;
    this.postCommentRepository = postCommentRepository;
  }

  Date date = new Date();
  Users user = Users.builder()
      .id(1)
      .name("Name")
      .build();

  Posts post1 = Posts.builder()
      .id(1)
      .time(date)
      .user(user)
      .title("Title")
      .build();


  public PostResponse getPosts(int limit, int offset, String mode) {

//    post1.setViewCount(10);
//    post1.setTitle("Title");
//    post1.setTime(date);
//    post1.setId(1);
//    post1.setText("Some text some text");
//    post1.setUser(user);

//    postRepository.save(post1);

    Pageable sortedByIdAndDesc =
        PageRequest.of(offset, limit, Sort.by("time").ascending());

    Page<Posts> allPosts = postRepository
        .findByModerationStatus(ModerationStatus.ACCEPTED, sortedByIdAndDesc);

    PostResponse postResponse = new PostResponse();

    postResponse.setCount(1);
    postResponse.setPostsPage(allPosts);
    postResponse.setAnnounce("Some announce");
    postResponse.setLikesCount(6);
    postResponse.setDisLikeCount(2);
    postResponse.setCommentCount(10);
    postResponse.setViewCount(20);


    return postResponse;
  }
}
