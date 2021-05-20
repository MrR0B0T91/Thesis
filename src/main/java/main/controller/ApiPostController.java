package main.controller;

import main.api.requset.LikeDislikeRequest;
import main.api.requset.PostRequest;
import main.api.response.LikeDislikeResponse;
import main.api.response.PostByIdResponse;
import main.api.response.PostResponse;
import main.api.response.PostingResponse;
import main.service.PostService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
  public PostResponse postSearch(
      @RequestParam(value = "offset", defaultValue = "0") int offset,
      @RequestParam(value = "limit", defaultValue = "10") int limit,
      @RequestParam(value = "query", defaultValue = "") String query) {

    return postService.getPost(offset, limit, query);
  }

  @GetMapping("/byDate")
  public PostResponse searchByDate(@RequestParam(value = "offset", defaultValue = "0") int offset,
      @RequestParam(value = "limit", defaultValue = "10") int limit,
      @RequestParam(value = "date") String date) {

    return postService.getPostsByDate(offset, limit, date);
  }

  @GetMapping("/byTag")
  public PostResponse searchByTag(@RequestParam(value = "offset", defaultValue = "0") int offset,
      @RequestParam(value = "limit", defaultValue = "10") int limit,
      @RequestParam(value = "tag") String tag) {

    return postService.getPostsByTag(offset, limit, tag);
  }

  @GetMapping("/{id}")
  public PostByIdResponse getPostById(@PathVariable int id) {

    return postService.getPostById(id);
  }

  @GetMapping("/moderation")
  @PreAuthorize("hasAuthority('user:moderate')")
  public PostResponse moderationPosts(
      @RequestParam(value = "offset", defaultValue = "0") int offset,
      @RequestParam(value = "limit", defaultValue = "10") int limit,
      @RequestParam(value = "status") String status) {
    return postService.getModerationPosts(offset, limit, status);
  }

  @GetMapping("/my")
  @PreAuthorize("hasAuthority('user:write')")
  public PostResponse myPosts(@RequestParam(value = "offset", defaultValue = "0") int offset,
      @RequestParam(value = "limit", defaultValue = "10") int limit,
      @RequestParam(value = "status") String status) {
    return postService.getMyPosts(offset, limit, status);
  }

  @PostMapping
  @PreAuthorize("hasAuthority('user:write')")
  public PostingResponse tweetPost(@RequestBody PostRequest postRequest) {
    return postService.makePost(postRequest);
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasAuthority('user:write')")
  public PostingResponse updatePost(@PathVariable int id,
      @RequestBody PostRequest postRequest) {
    return postService.updatePost(id, postRequest);
  }

  @PostMapping("/like")
  @PreAuthorize("hasAuthority('user:write')")
  public LikeDislikeResponse like(@RequestBody LikeDislikeRequest likeDislikeRequest) {
    return postService.like(likeDislikeRequest);
  }

  @PostMapping("/dislike")
  @PreAuthorize("hasAuthority('user:write')")
  public LikeDislikeResponse dislike(@RequestBody LikeDislikeRequest likeDislikeRequest) {
    return postService.dislike(likeDislikeRequest);
  }
}