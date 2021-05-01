package main.controller;

import main.api.requset.LoginRequest;
import main.api.response.CaptchaResponse;
import main.api.response.CheckResponse;
import main.api.response.LoginResponse;
import main.api.response.UserLoginResponse;
import main.model.repositories.UserRepository;
import main.service.CaptchaService;
import main.service.CheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class ApiAuthController {

  private final CheckService checkService;
  private final CaptchaService captchaService;
  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;

  @Autowired
  public ApiAuthController(CheckService checkService, CaptchaService captchaService,
      AuthenticationManager authenticationManager,
      UserRepository userRepository) {
    this.checkService = checkService;
    this.captchaService = captchaService;
    this.authenticationManager = authenticationManager;
    this.userRepository = userRepository;
  }

  @GetMapping("/check")
  private CheckResponse check() {
    return checkService.getResult();
  }

  @GetMapping("/captcha")
  private CaptchaResponse captcha() {
    return captchaService.getCaptcha();
  }

  @PostMapping("/login")
  public LoginResponse login(@RequestBody LoginRequest loginRequest) {
    Authentication auth = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
            loginRequest.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(auth);

    User user = (User) auth.getPrincipal();

    main.model.User currentUser = userRepository.findByEmail(user.getUsername());

    UserLoginResponse userLoginResponse = new UserLoginResponse();
    LoginResponse loginResponse = new LoginResponse();

    userLoginResponse.setId(currentUser.getId());
    userLoginResponse.setEmail(currentUser.getEmail());
    userLoginResponse.setModeration(currentUser.getIsModerator() == 1);
    userLoginResponse.setName(currentUser.getName());
    userLoginResponse.setPhoto(currentUser.getPhoto());
    userLoginResponse.setSettings(true);

    loginResponse.setResult(true);
    loginResponse.setUserLoginResponse(userLoginResponse);

    return loginResponse;
  }
}
