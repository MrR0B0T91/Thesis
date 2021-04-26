package main.service;

import com.github.cage.Cage;
import java.util.Base64;
import java.util.Random;
import main.api.response.CaptchaResponse;
import org.springframework.stereotype.Service;
import com.github.cage.GCage;

@Service
public class CaptchaService {

  private CaptchaResponse captchaResponse = new CaptchaResponse();

  public CaptchaResponse getCaptcha() {

    String secret = generateSecret(22);
    captchaResponse.setSecret(secret);

    Cage cage = new GCage();
    String token = cage.getTokenGenerator().next();
    byte[] content = cage.draw(token);
    String encodedString = Base64
        .getEncoder()
        .encodeToString(content);
    String image = "data:image/png;base64, " + encodedString;
    captchaResponse.setImage(image);

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
}
