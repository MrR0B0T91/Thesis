package main.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import main.api.requset.ProfileRequest;
import main.api.response.ProfileResponse;
import main.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
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

      currentUser.setPhoto(realPath);
      profileResponse.setPhotoPath(uploadPath + "/" + resultFileName);
    }
    userRepository.save(currentUser);
    profileResponse.setResult(true);

    return profileResponse;
  }
}
