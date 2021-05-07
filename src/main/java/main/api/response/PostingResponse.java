package main.api.response;

import lombok.Data;

@Data
public class PostingResponse {

  private boolean result;
  private PostErrorResponse errors;
}
