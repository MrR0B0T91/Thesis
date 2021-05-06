package main.controller;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import main.api.requset.LoginRequest;
import main.api.response.CaptchaResponse;
import main.api.response.CheckResponse;
import main.api.response.LoginResponse;
import main.api.response.LogoutResponse;
import main.api.response.RegisterResponse;
import main.api.response.UserLoginResponse;
import main.model.repositories.UserRepository;
import main.service.CaptchaService;
import main.service.CheckService;
import main.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
  private final RegisterService registerService;

  @Autowired
  public ApiAuthController(CheckService checkService, CaptchaService captchaService,
      AuthenticationManager authenticationManager,
      UserRepository userRepository, RegisterService registerService) {
    this.checkService = checkService;
    this.captchaService = captchaService;
    this.authenticationManager = authenticationManager;
    this.userRepository = userRepository;
    this.registerService = registerService;
  }

  @GetMapping("/check")
  public CheckResponse check() {
    return checkService.getResult();
  }

  @GetMapping("/captcha")
  public CaptchaResponse captcha() {
    return captchaService.getCaptcha();
  }

  @PostMapping("/register/{email}")
  @Validated
  public RegisterResponse register(
      @PathVariable("email") @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
          String email,
      @PathVariable("password") @Size(min = 6, message = "Пароль короче 6-ти символов")
          String password,
      @PathVariable("name") @Pattern(regexp = "^(?=.{8,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$")
          String name,
      String captcha, String captchaSecret) {

    return registerService.register(email, password, name, captcha, captchaSecret);
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

  @GetMapping("/logout")
  @PreAuthorize("hasAuthority('user:write')")
  public LogoutResponse logout() {
    LogoutResponse logoutResponse = new LogoutResponse();
    logoutResponse.setResult(true);
    SecurityContextHolder.getContext().setAuthentication(null);

    return logoutResponse;
  }
}
