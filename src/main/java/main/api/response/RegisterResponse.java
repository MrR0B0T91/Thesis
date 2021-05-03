package main.api.response;

import lombok.Data;

@Data
public class RegisterResponse {

  private boolean result;
  private ErrorsResponse errors;
}
