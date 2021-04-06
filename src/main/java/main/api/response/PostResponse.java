package main.api.response;

import lombok.Data;
import main.dto.PostDto;
import main.model.Posts;

import java.util.List;

@Data
public class PostResponse {

  private int count;
  private List<PostDto> posts;
}
