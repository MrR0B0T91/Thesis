package main.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class PostDto {

    private int id;
    private Date time;
    @JsonProperty("user")
    private UserDto userDto;
    private String title;
    private String text;
    private int likeCount;
    private int dislikeCount;
    private int commentCount;
    private int viewCount;
}
