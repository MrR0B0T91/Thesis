package main.dto;

import lombok.Data;

@Data
public class ErrorsDto {

  private String email;
  private String name;
  private String password;
  private String captcha;
}
