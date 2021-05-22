package main.api.response;

import lombok.Data;
import main.dto.PostErrorDto;

@Data
public class PostingResponse {

  private boolean result;
  private PostErrorDto errors;
}
