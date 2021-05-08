package main.api.response;

import lombok.Data;

@Data
public class CommentResponse {

  private int id;
  private boolean result;
  private String text;
}
