package main.api.response;

import lombok.Data;
import main.dto.ErrorsDto;

@Data
public class RegisterResponse {

    private boolean result;

    private ErrorsDto errors;
}
