package main.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import main.dto.UserLoginDto;

@Data
public class LoginResponse {

    @JsonProperty("result")
    private boolean result;

    @JsonProperty("user")
    private UserLoginDto userLoginDto;
}
