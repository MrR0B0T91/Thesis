package main.api.response;

import java.util.List;
import lombok.Data;
import main.dto.TagDto;

@Data
public class TagResponse {

  private List<TagDto> tags;
}
