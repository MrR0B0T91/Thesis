package main.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PostDto {

    @JsonProperty("id")
    private int id;

    @JsonProperty("timestamp")
    private long timeStamp;

    @JsonProperty("user")
    private UserDto userDto;

    @JsonProperty("title")
    private String title;

    @JsonProperty("announce")
    private String announce;

    @JsonProperty("likeCount")
    private int likeCount;

    @JsonProperty("dislikeCount")
    private int dislikeCount;

    @JsonProperty("commentCount")
    private int commentCount;

    @JsonProperty("viewCount")
    private int viewCount;
}
