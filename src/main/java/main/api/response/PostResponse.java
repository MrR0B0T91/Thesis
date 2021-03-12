package main.api.response;

import java.util.List;
import lombok.Data;
import main.model.Posts;

@Data
public class PostResponse {

  private int count;
  private List<Posts> posts;

}
