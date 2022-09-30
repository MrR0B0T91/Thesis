package main.api.requset;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModerateRequest {

    @JsonProperty("post_id")
    private int postId;

    @JsonProperty("decision")
    private String decision;
}
