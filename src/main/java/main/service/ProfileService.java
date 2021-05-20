package main.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.Optional;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import main.api.requset.ProfileRequest;
import main.api.response.ProfileResponse;
import main.dto.ProfileErrors;
import main.model.repositories.UserRepository;
import org.apache.commons.io.FileUtils;
import org.imgscalr.Scalr;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

  private final UserRepository userRepository;
  private final long LIMIT_TO_UPLOAD = 5242880;

  public ProfileService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public ProfileResponse multipartProfile(byte[] photo, String name, String email, int removePhoto,
      String password, HttpServletRequest request, Principal principal) {

    ProfileResponse profileResponse = new ProfileResponse();
    ProfileErrors profileErrors = new ProfileErrors();
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    if (photo.length > LIMIT_TO_UPLOAD) {
      profileErrors.setPhoto("Фото слишком большое, нужно меньше 5 Мб");
      profileResponse.setErrors(profileErrors);
      profileResponse.setResult(false);
    } else {

      String uploadPath = "upload/" + principal.hashCode() + ".jpg";
      String realPath = request.getServletContext().getRealPath(uploadPath);

      File file = new File(realPath);
      ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(photo);
      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

      try {
        BufferedImage bufferedImage = ImageIO.read(byteArrayInputStream);
        BufferedImage resizedImage = Scalr.resize(bufferedImage, 36);
        ImageIO.write(resizedImage, "jpg", byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        FileUtils.writeByteArrayToFile(file, bytes);
      } catch (IOException e) {
        e.printStackTrace();
      }

      main.model.User modelUser = userRepository.findByEmail(principal.getName());

      Optional<main.model.User> optionalUser = userRepository.findUserByEmail(email);
      if (optionalUser.isEmpty()) {
        modelUser.setEmail(email);
        profileResponse.setResult(true);
      } else {
        profileErrors.setEmail("Этот e-mail уже зарегестрирован");
        profileResponse.setResult(false);
      }

      if (password != null) {
        String encodedPassword = passwordEncoder.encode(password);
        modelUser.setPassword(encodedPassword);
      }

      if (removePhoto == 1) {
        modelUser.setPhoto("");
      }

      modelUser.setPhoto(uploadPath);
      modelUser.setName(name);
      userRepository.save(modelUser);
    }
    return profileResponse;
  }

  public ProfileResponse jsonProfile(ProfileRequest profileRequest) {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    User user = (User) authentication.getPrincipal();
    main.model.User currentUser = userRepository.findByEmail(user.getUsername());

    ProfileResponse profileResponse = new ProfileResponse();
    ProfileErrors profileErrors = new ProfileErrors();
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    String newName = profileRequest.getName();
    String newEmail = profileRequest.getEmail();
    String newPassword = profileRequest.getPassword();
    if (newPassword != null) {
      String encodedPassword = passwordEncoder.encode(newPassword);
      currentUser.setPassword(encodedPassword);
    }

    currentUser.setName(newName);

    Optional<main.model.User> optionalUser = userRepository.findUserByEmail(newEmail);
    if (optionalUser.isEmpty()) {
      currentUser.setEmail(newEmail);
      profileResponse.setResult(true);
    } else {
      profileErrors.setEmail("Этот e-mail уже зарегестрирован");
      profileResponse.setResult(false);
    }

    Integer remove = profileRequest.getRemovePhoto();
    if ((remove == null) || (remove == 0)) {
      currentUser.setPhoto(currentUser.getPhoto());
    } else {
      currentUser.setPhoto(profileRequest.getPhoto());
    }
    userRepository.save(currentUser);
    return null;
  }
}
