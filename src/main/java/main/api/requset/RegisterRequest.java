package main.api.requset;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RegisterRequest {

    @JsonProperty("e_mail")
    private String email;

    @JsonProperty("password")
    private String password;

    @JsonProperty("name")
    private String name;

    @JsonProperty("captcha")
    private String captcha;

    @JsonProperty("captcha_secret")
    private String captchaSecret;

}
