package main.service;

import java.util.Optional;
import main.api.requset.LoginRequest;
import main.api.response.LoginResponse;
import main.dto.UserLoginDto;
import main.model.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;

  public LoginService(
      AuthenticationManager authenticationManager,
      UserRepository userRepository) {
    this.authenticationManager = authenticationManager;
    this.userRepository = userRepository;
  }

  public LoginResponse loginUser(LoginRequest loginRequest) {

    UserLoginDto userLoginDto = new UserLoginDto();
    LoginResponse loginResponse = new LoginResponse();
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    Optional<main.model.User> optionalUser = userRepository
        .findUserByEmail(loginRequest.getEmail());

    if ((optionalUser.isPresent()) && (passwordEncoder
        .matches(loginRequest.getPassword(), optionalUser.get().getPassword()))) {
      Authentication auth = authenticationManager
          .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
              loginRequest.getPassword()));
      SecurityContextHolder.getContext().setAuthentication(auth);

      User user = (User) auth.getPrincipal();
      main.model.User currentUser = userRepository.findByEmail(user.getUsername());

      userLoginDto.setId(currentUser.getId());
      userLoginDto.setEmail(currentUser.getEmail());
      userLoginDto.setModeration(currentUser.getIsModerator() == 1);
      userLoginDto.setName(currentUser.getName());
      userLoginDto.setPhoto(currentUser.getPhoto());
      userLoginDto.setSettings(currentUser.getIsModerator() == 1);

      loginResponse.setResult(true);
      loginResponse.setUserLoginDto(userLoginDto);
    } else {
      loginResponse.setResult(false);
    }
    return loginResponse;
  }

}
