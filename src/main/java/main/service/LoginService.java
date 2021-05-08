package main.service;

import java.util.Optional;
import main.api.requset.LoginRequest;
import main.api.response.LoginResponse;
import main.api.response.UserLoginResponse;
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

    UserLoginResponse userLoginResponse = new UserLoginResponse();
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

      userLoginResponse.setId(currentUser.getId());
      userLoginResponse.setEmail(currentUser.getEmail());
      userLoginResponse.setModeration(currentUser.getIsModerator() == 1);
      userLoginResponse.setName(currentUser.getName());
      userLoginResponse.setPhoto(currentUser.getPhoto());
      userLoginResponse.setSettings(currentUser.getIsModerator() == 1);

      loginResponse.setResult(true);
      loginResponse.setUserLoginResponse(userLoginResponse);
    } else {
      loginResponse.setResult(false);
    }
    return loginResponse;
  }

}
