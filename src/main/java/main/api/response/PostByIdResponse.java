package main.api.response;

import java.util.List;
import lombok.Data;
import main.dto.CommentDto;
import main.dto.UserDto;

@Data
public class PostByIdResponse {

  private int id;
  private long timestamp;
  private UserDto user;
  private String title;
  private String text;
  private int likeCount;
  private int dislikeCount;
  private int viewCount;
  private List<CommentDto> comments;
  private List<String> tags;
}
