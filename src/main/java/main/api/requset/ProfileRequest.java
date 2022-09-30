package main.api.requset;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileRequest {

    private String name;

    private String email;

    private String password;

    private Integer removePhoto;

    private String photo;
}
