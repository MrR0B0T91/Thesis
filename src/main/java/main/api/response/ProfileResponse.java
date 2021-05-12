package main.api.response;

import lombok.Data;
import main.dto.ProfileErrors;

@Data
public class ProfileResponse {

  private boolean result;
  private ProfileErrors errors;
}
