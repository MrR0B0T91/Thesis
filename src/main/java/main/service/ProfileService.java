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
import org.apache.commons.io.FileUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProfileService {

  private final UserRepository userRepository;

  public ProfileService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public ProfileResponse profile(ProfileRequest profileRequest, String name, String email,
      String password, Integer removePhoto, MultipartFile image, HttpServletRequest request) {
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

    int remove = profileRequest.getRemovePhoto();

    currentUser.setName(newName);

    Optional<main.model.User> optionalUser = userRepository.findUserByEmail(newEmail);
    if (optionalUser.isEmpty()) {
      currentUser.setEmail(newEmail);
      profileResponse.setResult(true);
    } else {
      profileErrors.setEmail("Этот e-mail уже зарегестрирован");
      profileResponse.setResult(false);
    }

    if ((removePhoto == 1) || (remove == 1)) {
      currentUser.setPhoto("");
    }
    userRepository.save(currentUser);

    String uploadPath = "D:/SkillBox/Diploma/upload/ab/cd/ef/";

    if (image != null && !image.getOriginalFilename().isEmpty()) {

      String uuidFile = UUID.randomUUID().toString();
      String resultFileName = uuidFile + "." + image.getOriginalFilename();

      String realPath = request.getServletContext().getRealPath(resultFileName);
      String copyPath = uploadPath + resultFileName;
      File original = new File(realPath);
      File uploadDir = new File(copyPath);

      try {
        image.transferTo(new File(realPath));
        FileUtils.copyFile(original, uploadDir);
      } catch (IOException e) {
        e.printStackTrace();
      }

      currentUser.setPhoto(copyPath);
      profileResponse.setPhotoPath(copyPath);
    }
    userRepository.save(currentUser);
    profileResponse.setResult(true);

    profileResponse.setErrors(profileErrors);

    return profileResponse;
  }
}
