package main.service;

import java.util.Date;
import main.api.requset.RegisterRequest;
import main.api.response.ErrorsResponse;
import main.api.response.RegisterResponse;
import main.model.CaptchaCodes;
import main.model.User;
import main.model.repositories.CaptchaCodeRepository;
import main.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

  private final UserRepository userRepository;
  private final CaptchaCodeRepository captchaCodeRepository;
  private final int MIN_LENGTH = 6;

  private ErrorsResponse errorsResponse = new ErrorsResponse();
  private RegisterResponse registerResponse = new RegisterResponse();

  @Autowired
  public RegisterService(UserRepository userRepository,
      CaptchaCodeRepository captchaCodeRepository) {
    this.userRepository = userRepository;
    this.captchaCodeRepository = captchaCodeRepository;
  }

  public RegisterResponse register(RegisterRequest registerRequest) {

    CaptchaCodes repoCaptcha = captchaCodeRepository
        .findBySecret(registerRequest.getCaptchaSecret());

    boolean dataCorrect = (checkCaptcha(registerRequest, repoCaptcha) && (checkEmail(
        registerRequest)) && (checkPassword(registerRequest)));

    if (dataCorrect) {
      User user = new User();

      user.setEmail(registerRequest.getEmail());
      user.setName(registerRequest.getName());
      user.setPassword(registerRequest.getPassword());
      user.setIsModerator(0);
      user.setRegTime(new Date());

      userRepository.save(user);
      registerResponse.setResult(true);
    } else {
      if (!checkEmail(registerRequest)) {
        errorsResponse.setEmail("Этот email уже зарегестрирован");
      }
      if (!checkPassword(registerRequest)) {
        errorsResponse.setPassword("Пароль короче 6-ти символов");
      }
      if (!checkCaptcha(registerRequest, repoCaptcha)) {
        errorsResponse.setCaptcha("Код с картинки введен неверно");
      }
      registerResponse.setResult(false);
      registerResponse.setErrors(errorsResponse);
    }
    return registerResponse;
  }

  private boolean checkCaptcha(RegisterRequest registerRequest, CaptchaCodes repoCaptcha) {
    boolean check = true;
    boolean captchaCorrect = registerRequest.getCaptcha()
        .equals(repoCaptcha.getCode()); // не корректная проверка
    if (!captchaCorrect) {
      check = false;
    }
    return check;
  }

  private boolean checkEmail(RegisterRequest registerRequest) {
    boolean check = true;
    String email = registerRequest.getEmail();
    User repoUser = userRepository.findByEmail(email);
    if (repoUser.getEmail().equals(email)) {
      check = false;
    }
    return check;
  }

  private boolean checkPassword(RegisterRequest registerRequest) {
    boolean check = true;
    String password = registerRequest.getPassword();
    if (password.length() < MIN_LENGTH) {
      check = false;
    }
    return check;
  }
}
