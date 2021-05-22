package main.api.response;

import lombok.Data;
import main.dto.PasswordErrorsDto;

@Data
public class PasswordResponse {

  private boolean result;
  private PasswordErrorsDto errors;
}
