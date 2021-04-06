package main.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PostDto {

  private int id;

  @JsonProperty("timestamp")
  private long timeStamp;

  @JsonProperty("user")
  private UserDto userDto;

  private String title;
  private String text;
  private int likeCount;
  private int dislikeCount;
  private int commentCount;
  private int viewCount;
}
