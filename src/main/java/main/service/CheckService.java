package main.service;

import main.api.response.CheckResponse;
import main.model.Users;
import main.model.repositories.PostRepository;
import main.model.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CheckService {

  private final UserRepository userRepository;
  private final PostRepository postRepository;

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

    if (user.isModerator()) {
      checkResponse.setResult(true);
      checkResponse.setUser(user);
      checkResponse.setModeration(true);
      checkResponse.setModerationCount(56);
      checkResponse.setSettings(true);
    } else {
      checkResponse.setResult(false);
    }
    return checkResponse;
  }
}
