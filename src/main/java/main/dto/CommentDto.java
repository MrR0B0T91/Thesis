package main.dto;

import lombok.Data;

@Data
public class CommentDto {

    private int id;

    private long timestamp;

    private String text;

    private CommentUserDto user;
}
