package main.api.response;

import java.util.List;
import lombok.Data;
import main.dto.PostDto;

@Data
public class PostResponse {

  private long count;
  private List<PostDto> posts;
}
