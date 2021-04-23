package main.controller;

import main.api.response.CaptchaResponse;
import main.api.response.CheckResponse;
import main.service.CaptchaService;
import main.service.CheckService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class ApiAuthController {

  private final CheckService checkService;
  private final CaptchaService captchaService;

  public ApiAuthController(CheckService checkService, CaptchaService captchaService) {
    this.checkService = checkService;
    this.captchaService = captchaService;
  }

  @GetMapping("/check")
  private CheckResponse check() {
    return checkService.getResult();
  }

  @GetMapping("/captcha")
  private CaptchaResponse captcha(){
    return captchaService.getCaptcha();
  }

}
