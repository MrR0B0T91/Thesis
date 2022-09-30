package main.api.requset;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {

    private long timestamp;

    private int active;

    private String title;

    private List<String> tags;

    private String text;
}
