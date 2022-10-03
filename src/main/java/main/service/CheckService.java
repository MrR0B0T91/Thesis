package main.service;

import java.util.List;

import main.api.response.CheckResponse;
import main.dto.UserLoginDto;
import main.model.enums.ModerationStatus;
import main.model.Posts;
import main.model.repositories.PostRepository;
import main.model.repositories.UserRepository;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class CheckService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    private int moderationCount;

    public CheckService(
            PostRepository postRepository, UserRepository userRepository) {

        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public CheckResponse getResult() {

        CheckResponse checkResponse = new CheckResponse();
        UserLoginDto userLoginDto = new UserLoginDto();

        List<Posts> newPosts = postRepository.findAllByModerationStatus(ModerationStatus.NEW);
        moderationCount = newPosts.size();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {

            User user = (User) authentication.getPrincipal();
            main.model.User currentUser = userRepository.findByEmail(user.getUsername());

            userLoginDto.setId(currentUser.getId());
            userLoginDto.setName(currentUser.getName());
            userLoginDto.setPhoto(currentUser.getPhoto());
            userLoginDto.setEmail(currentUser.getEmail());
            userLoginDto.setModeration(currentUser.getIsModerator() == 1);
            userLoginDto.setSettings(true);

            if (userLoginDto.isModeration()) {
                userLoginDto.setModerationCount(moderationCount);
            } else {
                userLoginDto.setModerationCount(0);
            }
            checkResponse.setResult(true);
            checkResponse.setUser(userLoginDto);
        } else {
            checkResponse.setResult(false);
        }
        return checkResponse;
    }
}
