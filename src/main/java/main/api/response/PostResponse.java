package main.api.response;

import lombok.Builder;
import lombok.Data;
import main.model.Posts;
import org.springframework.data.domain.Page;

@Data
public class PostResponse {

  private long count;
  private Page<Posts> postsPage;
  private String announce;
  private int likesCount;
  private int disLikeCount;
  private int commentCount;
  private int viewCount;
}
