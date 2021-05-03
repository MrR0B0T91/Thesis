package main.controller;

import main.api.response.PostByIdResponse;
import main.api.response.PostResponse;
import main.service.PostService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/post")
public class ApiPostController {

  private final PostService postService;

  public ApiPostController(PostService postService) {
    this.postService = postService;
  }

  @GetMapping
  public PostResponse posts(
      @RequestParam(value = "offset", defaultValue = "0") int offset,
      @RequestParam(value = "limit", defaultValue = "10") int limit,
      @RequestParam(value = "mode", defaultValue = "recent") String mode) {

    return postService.getPosts(offset, limit, mode);
  }

  @GetMapping("/search")
  @PreAuthorize("hasAuthority('user:write')")
  public PostResponse postSearch(
      @RequestParam(value = "offset", defaultValue = "0") int offset,
      @RequestParam(value = "limit", defaultValue = "10") int limit,
      @RequestParam(value = "query", defaultValue = "") String query) {

    return postService.getPost(offset, limit, query);
  }

  @GetMapping("/byDate")
  @PreAuthorize("hasAuthority('user:write')")
  public PostResponse searchByDate(@RequestParam(value = "offset", defaultValue = "0") int offset,
      @RequestParam(value = "limit", defaultValue = "10") int limit,
      @RequestParam(value = "date") String date) {

    return postService.getPostsByDate(offset, limit, date);
  }

  @GetMapping("/byTag")
  @PreAuthorize("hasAuthority('user:write')")
  public PostResponse searchByTag(@RequestParam(value = "offset", defaultValue = "0") int offset,
      @RequestParam(value = "limit", defaultValue = "10") int limit,
      @RequestParam(value = "tag") String tag) {

    return postService.getPostsByTag(offset, limit, tag);
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAuthority('user:write')")
  public PostByIdResponse getPostById(@PathVariable int id) {

    return postService.getPostById(id);
  }
}