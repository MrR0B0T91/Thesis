package main.api.requset;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileRequest {

  private String name;
  private String email;
  private String password;
  private MultipartFile photo;
  private int removePhoto;
}
