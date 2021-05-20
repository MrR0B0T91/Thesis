package main.dto;

import lombok.Data;

@Data
public class PasswordErrors {

  private String code;
  private String password;
  private String captcha;
}
