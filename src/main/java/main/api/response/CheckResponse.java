package main.api.response;

import lombok.Data;
import main.model.Users;

@Data
public class CheckResponse {

  private boolean result;
  private Users user;
  private boolean moderation;
  private int moderationCount;
  private boolean settings;

}
