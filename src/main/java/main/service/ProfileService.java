package main.service;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import main.api.requset.ProfileRequest;
import main.api.response.ProfileResponse;
import main.dto.ProfileErrors;
import main.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProfileService {

  @Value("${upload.path}")
  private String uploadPath;

  private final UserRepository userRepository;

  public ProfileService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public ProfileResponse profile(ProfileRequest profileRequest, HttpServletRequest request) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    User user = (User) authentication.getPrincipal();
    main.model.User currentUser = userRepository.findByEmail(user.getUsername());
    ProfileResponse profileResponse = new ProfileResponse();
    ProfileErrors profileErrors = new ProfileErrors();
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

//    String newName = profileRequest.getName();
//    String newEmail = profileRequest.getEmail();
//    String newPassword = profileRequest.getPassword();
//    if (newPassword != null) {
//      String encodedPassword = passwordEncoder.encode(newPassword);
//      currentUser.setPassword(encodedPassword);
//    }
//
//    int removePhoto = profileRequest.getRemovePhoto();
//
//    currentUser.setName(newName);
//    Optional<main.model.User> optionalUser = userRepository.findUserByEmail(newEmail);
//    if (optionalUser.isEmpty()) {
//      currentUser.setEmail(newEmail);
//      profileResponse.setResult(true);
//    } else {
//      profileErrors.setEmail("Этот e-mail уже зарегестрирован");
//      profileResponse.setResult(false);
//    }
//
//    if (removePhoto == 1) {
//      currentUser.setPhoto("");
//    }
//    userRepository.save(currentUser);

    MultipartFile image = profileRequest.getPhoto();

    if (image != null && !image.getOriginalFilename().isEmpty()) {
      File uploadDir = new File(uploadPath);

      if (!uploadDir.exists()) {
        uploadDir.mkdir();
      }

      String uuidFile = UUID.randomUUID().toString();
      String resultFileName = uuidFile + "." + image.getOriginalFilename();

      String realPath = request.getServletContext().getRealPath(uploadPath + "/" + resultFileName);

      try {
        image.transferTo(new File(realPath));
      } catch (IOException e) {
        e.printStackTrace();
      }

      currentUser.setPhoto(uploadPath + "/" + resultFileName);
      profileResponse.setPhotoPath(uploadPath + "/" + resultFileName);
    }
    userRepository.save(currentUser);
    profileResponse.setResult(true);

    profileResponse.setErrors(profileErrors);
    return profileResponse;
  }
}
