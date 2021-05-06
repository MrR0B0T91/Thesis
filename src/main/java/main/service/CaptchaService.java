package main.service;

import com.github.cage.Cage;
import com.github.cage.GCage;
import java.util.Base64;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import main.api.response.CaptchaResponse;
import main.model.CaptchaCodes;
import main.model.repositories.CaptchaCodeRepository;
import org.springframework.stereotype.Service;

@Service
public class CaptchaService {

  private final CaptchaCodeRepository captchaCodeRepository;

  public CaptchaService(CaptchaCodeRepository captchaCodeRepository) {
    this.captchaCodeRepository = captchaCodeRepository;
  }

  public CaptchaResponse getCaptcha() {

    CaptchaResponse captchaResponse = new CaptchaResponse();

    String secret = generateSecret(22);
    captchaResponse.setSecret(secret);

    Cage cage = new GCage();
    String token = cage.getTokenGenerator().next();
    byte[] byteImage = cage.draw(token);
    String encoded = Base64
        .getEncoder()
        .encodeToString(byteImage);
    String image = "data:image/png;base64, " + encoded;
    captchaResponse.setImage(image);

    CaptchaCodes captcha = new CaptchaCodes();

    Calendar currentDate = Calendar.getInstance();
    captcha.setCode(token);
    captcha.setSecretCode(secret);
    captcha.setTime(currentDate);

    captchaCodeRepository.save(captcha);

    List<CaptchaCodes> captchaCodesList = captchaCodeRepository.findAll();
    checkCaptchaCodes(captchaCodesList);

    return captchaResponse;
  }

  private String generateSecret(int length) {

    String source = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

    StringBuffer secretBuffer = new StringBuffer();
    Random random = new Random();

    while (secretBuffer.length() < length) {
      int index = (int) (random.nextFloat() * source.length());
      secretBuffer.append(source.substring(index, index + 1));
    }
    return secretBuffer.toString();
  }

  private void checkCaptchaCodes(List<CaptchaCodes> captchaCodesList) {

    Calendar currentDate = Calendar.getInstance();
    for (CaptchaCodes captchaCode : captchaCodesList) {
      Calendar captchaDate = captchaCode.getTime();
      int captchaHour = captchaDate.get(Calendar.HOUR);
      int currentHour = currentDate.get(Calendar.HOUR);
      boolean moreThanHour = (currentHour - captchaHour) > 1;
      if ((captchaDate.getTime().before(currentDate.getTime())) && (moreThanHour)) {
        captchaCodeRepository.delete(captchaCode);
      }
    }
  }
}
