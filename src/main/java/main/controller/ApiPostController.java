package main.controller;

import main.api.response.PostResponse;
import main.service.PostService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post")
public class ApiPostController {

  private final PostService postService;

  public ApiPostController(PostService postService) {
    this.postService = postService;
  }

  @GetMapping
  @ResponseBody
  private PostResponse posts(
      @RequestParam(value = "offset", defaultValue = "0") int offset,
      @RequestParam(value = "limit", defaultValue = "10") int limit,
      @RequestParam(value = "mode", defaultValue = "recent") String mode) {

    return postService.getPosts(offset, limit, mode);
  }
}
