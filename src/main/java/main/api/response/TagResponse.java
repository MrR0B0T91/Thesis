package main.api.response;

import java.util.HashMap;
import lombok.Data;

@Data
public class TagResponse {

  private HashMap<String, Double> tags;
}
