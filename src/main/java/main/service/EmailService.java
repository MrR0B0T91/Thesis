package main.service;

import java.util.Optional;
import java.util.UUID;
import main.api.requset.PasswordRequest;
import main.api.requset.RestoreRequest;
import main.api.response.LikeDislikeResponse;
import main.api.response.PasswordResponse;
import main.config.EmailConfig;
import main.dto.PasswordErrors;
import main.model.CaptchaCodes;
import main.model.User;
import main.model.repositories.CaptchaCodeRepository;
import main.model.repositories.UserRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

  private final UserRepository userRepository;
  private final EmailConfig emailConfig;
  private final CaptchaCodeRepository captchaCodeRepository;

  public EmailService(UserRepository userRepository, EmailConfig emailConfig,
      CaptchaCodeRepository captchaCodeRepository) {
    this.userRepository = userRepository;
    this.emailConfig = emailConfig;
    this.captchaCodeRepository = captchaCodeRepository;
  }

  public LikeDislikeResponse restorePassword(RestoreRequest restoreRequest) {
    LikeDislikeResponse response = new LikeDislikeResponse();
    String HASH = UUID.randomUUID().toString();

    Optional<User> optionalUser = userRepository.findUserByEmail(restoreRequest.getEmail());
    if (optionalUser.isPresent()) {
      User user = optionalUser.get();
      user.setCode(HASH);
      userRepository.save(user);

      String message = String.format(
          "Hello, %s \n"
              + "To restore password please link: http://localhost:8080/login/change-password/%s",
          user.getName(), HASH
      );

      send(user.getEmail(), "Restore Password", message);

      response.setResult(true);
    }

    return response;
  }

  public void send(String emailTo, String subject, String message) {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

    mailSender.setHost(this.emailConfig.getHost());
    mailSender.setPort(this.emailConfig.getPort());
    mailSender.setUsername(this.emailConfig.getUsername());
    mailSender.setPassword(this.emailConfig.getPassword());

    SimpleMailMessage mailMessage = new SimpleMailMessage();

    mailMessage.setFrom(this.emailConfig.getUsername());
    mailMessage.setTo(emailTo);
    mailMessage.setSubject(subject);
    mailMessage.setText(message);

    mailSender.send(mailMessage);
  }

  public PasswordResponse password(PasswordRequest passwordRequest) {
    PasswordResponse passwordResponse = new PasswordResponse();
    PasswordErrors passwordErrors = new PasswordErrors();
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    User user = userRepository.findByCode(passwordRequest.getCode());
    if (user == null) {
      passwordResponse.setResult(false);
      passwordErrors.setCode(
          "Ссылка для восстановления пароля устарела. <a href= \"/auth/restore\"> Запросить ссылку снова</a>");
    } else {

      if (passwordRequest.getPassword().length() >= 6) {
        user.setPassword(encoder.encode(passwordRequest.getPassword()));
      } else {
        passwordResponse.setResult(false);
        passwordErrors.setPassword("Пароль короче 6-ти символов");
      }

      CaptchaCodes captchaCodes = captchaCodeRepository.findBySecret(
          passwordRequest.getCaptchaSecret());

      if (checkCaptcha(passwordRequest.getCaptcha(), captchaCodes)) {
        userRepository.save(user);
      } else {
        passwordResponse.setResult(false);
        passwordErrors.setCaptcha("Код с картинки введен неверно");
      }
    }
    passwordResponse.setErrors(passwordErrors);
    return passwordResponse;
  }

  private boolean checkCaptcha(String captcha, CaptchaCodes repoCaptcha) {
    boolean check = true;
    boolean captchaCorrect = captcha.equals(repoCaptcha.getCode());
    if (!captchaCorrect) {
      check = false;
    }
    return check;
  }
}
