package main.api.response;

import lombok.Data;
import main.dto.ProfileErrorsDto;

@Data
public class ProfileResponse {

  private boolean result;
  private ProfileErrorsDto errors;
}
