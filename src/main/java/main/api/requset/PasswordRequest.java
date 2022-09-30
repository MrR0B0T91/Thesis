package main.api.requset;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordRequest {

    @JsonProperty("code")
    private String code;

    @JsonProperty("password")
    private String password;

    @JsonProperty("captcha")
    private String captcha;

    @JsonProperty("captcha_secret")
    private String captchaSecret;
}
