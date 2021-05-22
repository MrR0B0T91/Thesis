package main.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import main.dto.UserLoginDto;

@Data
public class LoginResponse {

  private boolean result;
  @JsonProperty("user")
  private UserLoginDto userLoginDto;
}
