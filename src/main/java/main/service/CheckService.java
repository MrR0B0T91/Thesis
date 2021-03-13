package main.service;

import java.util.List;
import main.api.response.CheckResponse;
import main.model.ModerationStatus;
import main.model.Posts;
import main.model.Users;
import main.model.repositories.PostRepository;
import main.model.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CheckService {

  private final UserRepository userRepository;
  private final PostRepository postRepository;
  private int moderationCount;

  CheckResponse checkResponse = new CheckResponse();
  Users user = new Users();

  public CheckService(UserRepository userRepository,
      PostRepository postRepository) {
    this.userRepository = userRepository;
    this.postRepository = postRepository;
  }

  public CheckResponse getResult() {

    user.setId(1);
    user.setName("Jack");
    user.setPhoto("avatar");
    user.setEmail("email@.com");
    user.setModerator(true);

    List<Posts> newPosts = postRepository.findAllByModerationStatus(ModerationStatus.NEW);
    moderationCount = newPosts.size();

    if (user.isModerator()) {
      checkResponse.setResult(true);
      checkResponse.setUser(user);
      checkResponse.setModeration(true);
      checkResponse.setModerationCount(moderationCount);
      checkResponse.setSettings(true);
    } else {
      checkResponse.setResult(false);
    }
    return checkResponse;
  }
}
