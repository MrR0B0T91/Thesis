package main.api.response;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import lombok.Data;

@Data
public class CalendarResponse {

  private List<Integer> years;
  private HashMap<Date, Integer> posts;
}
