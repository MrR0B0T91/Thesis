package main.api.response;

import lombok.Data;
import main.dto.PasswordErrors;

@Data
public class PasswordResponse {

  private boolean result;
  private PasswordErrors errors;
}
