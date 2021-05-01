package main.api.response;

import lombok.Data;
import main.model.User;

@Data
public class CheckResponse {

  private boolean result;
  private UserLoginResponse user;
}
