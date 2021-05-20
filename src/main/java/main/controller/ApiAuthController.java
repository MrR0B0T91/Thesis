package main.controller;

import main.api.requset.LoginRequest;
import main.api.requset.PasswordRequest;
import main.api.requset.RegisterRequest;
import main.api.requset.RestoreRequest;
import main.api.response.CaptchaResponse;
import main.api.response.CheckResponse;
import main.api.response.LikeDislikeResponse;
import main.api.response.LoginResponse;
import main.api.response.LogoutResponse;
import main.api.response.PasswordResponse;
import main.api.response.RegisterResponse;
import main.service.CaptchaService;
import main.service.CheckService;
import main.service.EmailService;
import main.service.LoginService;
import main.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
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
  private final LoginService loginService;
  private final RegisterService registerService;
  private final EmailService emailService;

  @Autowired
  public ApiAuthController(CheckService checkService, CaptchaService captchaService,
      LoginService loginService, RegisterService registerService,
      EmailService emailService) {
    this.checkService = checkService;
    this.captchaService = captchaService;
    this.loginService = loginService;
    this.registerService = registerService;
    this.emailService = emailService;
  }

  @GetMapping("/check")
  public CheckResponse check() {
    return checkService.getResult();
  }

  @GetMapping("/captcha")
  public CaptchaResponse captcha() {
    return captchaService.getCaptcha();
  }

  @PostMapping("/register")
  public RegisterResponse register(@RequestBody RegisterRequest registerRequest) {

    return registerService.register(registerRequest);
  }

  @PostMapping("/login")
  public LoginResponse login(@RequestBody LoginRequest loginRequest) {

    return loginService.loginUser(loginRequest);
  }

  @GetMapping("/logout")
  @PreAuthorize("hasAuthority('user:write')")
  public LogoutResponse logout() {
    LogoutResponse logoutResponse = new LogoutResponse();
    logoutResponse.setResult(true);
    SecurityContextHolder.getContext().setAuthentication(null);

    return logoutResponse;
  }

  @PostMapping("/restore")
  public LikeDislikeResponse restore(@RequestBody RestoreRequest restoreRequest) {
    return emailService.restorePassword(restoreRequest);
  }

  @PostMapping("/password")
  public PasswordResponse password(@RequestBody PasswordRequest passwordRequest) {
    return emailService.password(passwordRequest);
  }
}
