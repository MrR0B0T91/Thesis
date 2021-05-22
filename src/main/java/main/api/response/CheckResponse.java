package main.api.response;

import lombok.Data;
import main.dto.UserLoginDto;

@Data
public class CheckResponse {

  private boolean result;
  private UserLoginDto user;
}
