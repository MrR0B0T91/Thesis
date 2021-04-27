package main.service;

import java.util.List;
import main.api.response.CheckResponse;
import main.model.ModerationStatus;
import main.model.Posts;
import main.model.Users;
import main.model.repositories.PostRepository;
import main.springsecurity.UserPrincipal;
import main.springsecurity.UserPrincipalDetailsService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CheckService {

  private final UserPrincipalDetailsService userPrincipalDetailsService;
  private final PostRepository postRepository;
  private int moderationCount;

  CheckResponse checkResponse = new CheckResponse();
  Users user = new Users();

  public CheckService(
      UserPrincipalDetailsService userPrincipalDetailsService,
      PostRepository postRepository) {
    this.userPrincipalDetailsService = userPrincipalDetailsService;

    this.postRepository = postRepository;
  }

  public CheckResponse getResult() {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (!(authentication instanceof AnonymousAuthenticationToken)) {
      String currentUserName = authentication.getName();
      UserPrincipal userPrincipal = (UserPrincipal) userPrincipalDetailsService
          .loadUserByUsername(currentUserName);

      user.setId(userPrincipal.getId());
      user.setName(currentUserName);
      user.setPhoto(userPrincipal.getPhoto());
      user.setEmail(userPrincipal.getEmail());
      user.setModerator(userPrincipal.isModerator());
    }

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
