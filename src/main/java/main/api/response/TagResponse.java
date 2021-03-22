package main.api.response;

import lombok.Data;

import java.util.HashMap;

@Data
public class TagResponse {
  private HashMap<String, Double> tags;
}
