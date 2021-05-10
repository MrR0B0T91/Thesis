package main.api.response;

import lombok.Data;

@Data
public class MyStatisticResponse {

  private int postsCount;
  private int likesCount;
  private int dislikesCount;
  private int viewsCount;
  private long firstPublication;
}
