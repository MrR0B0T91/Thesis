package main.api.response;

import java.util.HashMap;
import lombok.Data;
import main.model.Tags;

@Data
public class TagResponse {

  private HashMap<String, Double> tags;
}
