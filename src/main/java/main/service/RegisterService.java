package main.service;

import java.util.Date;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import main.api.requset.RegisterRequest;
import main.api.response.ErrorsResponse;
import main.api.response.RegisterResponse;
import main.model.CaptchaCodes;
import main.model.User;
import main.model.repositories.CaptchaCodeRepository;
import main.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

  private final UserRepository userRepository;
  private final CaptchaCodeRepository captchaCodeRepository;

  @Autowired
  public RegisterService(UserRepository userRepository,
      CaptchaCodeRepository captchaCodeRepository) {
    this.userRepository = userRepository;
    this.captchaCodeRepository = captchaCodeRepository;
  }

  public RegisterResponse register(RegisterRequest registerRequest) {

    ErrorsResponse errorsResponse = new ErrorsResponse();
    RegisterResponse registerResponse = new RegisterResponse();
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    CaptchaCodes repoCaptcha = captchaCodeRepository
        .findBySecret(registerRequest.getCaptchaSecret());

    boolean dataCorrect = (checkCaptcha(registerRequest.getCaptcha(), repoCaptcha) && (!checkEmail(
        registerRequest.getEmail())));

    if (dataCorrect) {
      User user = new User();
      String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());

      user.setEmail(registerRequest.getEmail());
      user.setName(registerRequest.getName());
      user.setPassword(encodedPassword);
      user.setIsModerator(0);
      user.setRegTime(new Date());

      userRepository.save(user);
      registerResponse.setResult(true);

    } else {
      if (checkEmail(registerRequest.getEmail())) {
        errorsResponse.setEmail("Этот email уже зарегестрирован");
      }
      if (!checkCaptcha(registerRequest.getCaptcha(), repoCaptcha)) {
        errorsResponse.setCaptcha("Код с картинки введен неверно");
      }
      if (!checkName(registerRequest.getName())) {
        errorsResponse.setName("Имя указано неверно");
      }
      registerResponse.setResult(false);
      registerResponse.setErrors(errorsResponse);
    }
    return registerResponse;
  }

  private boolean checkCaptcha(String captcha, CaptchaCodes repoCaptcha) {
    boolean check = true;
    boolean captchaCorrect = captcha.equals(repoCaptcha.getCode());
    if (!captchaCorrect) {
      check = false;
    }
    return check;
  }

  private boolean checkEmail(String email) {
    Optional<User> repoUser = userRepository.findUserByEmail(email);
    return repoUser.map(user -> user.getEmail().equals(email)).orElse(false);
  }

  private boolean checkName(String name) {
    Pattern pattern = Pattern.compile("^[\\p{L} .'-]+$");
    Matcher matcher = pattern.matcher(name);

    return matcher.matches();
  }
}
