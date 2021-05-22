package main.dto;

import lombok.Data;

@Data
public class PasswordErrorsDto {

  private String code;
  private String password;
  private String captcha;
}
