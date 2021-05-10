package main.controller;

import main.api.requset.CommentRequest;
import main.api.response.CalendarResponse;
import main.api.response.CommentResponse;
import main.api.response.InitResponse;
import main.api.response.SettingsResponse;
import main.api.response.StatisticsResponse;
import main.api.response.TagResponse;
import main.service.PostService;
import main.service.SettingsService;
import main.service.StatisticService;
import main.service.TagService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiGeneralController {

  private final InitResponse initResponse;
  private final SettingsService settingsService;
  private final TagService tagService;
  private final PostService postService;
  private final StatisticService statisticService;

  public ApiGeneralController(
      InitResponse initResponse, SettingsService settingsService, TagService tagService,
      PostService postService, StatisticService statisticService) {
    this.initResponse = initResponse;
    this.settingsService = settingsService;
    this.tagService = tagService;
    this.postService = postService;
    this.statisticService = statisticService;
  }

  @GetMapping("/init")
  public InitResponse init() {
    return initResponse;
  }

  @GetMapping("/settings")
  public SettingsResponse settings() {
    return settingsService.getGlobalSettings();
  }

  @GetMapping("/tag")
  public TagResponse tags(
      @RequestParam(value = "query", defaultValue = "") String name) {
    return tagService.getTags();
  }

  @GetMapping("/calendar")
  public CalendarResponse postsByYear(
      @RequestParam(value = "year", defaultValue = "") String year) {
    return postService.getPostsByYear(year);
  }

  @PostMapping("/comment")
  @PreAuthorize("hasAuthority('user:write')")
  public CommentResponse comment(@RequestBody CommentRequest commentRequest) {

    return postService.postComment(commentRequest);
  }

  @GetMapping("/statistics/my")
  @PreAuthorize("hasAuthority('user:write')")
  public StatisticsResponse myStatistics() {
    return statisticService.getMyStatistics();
  }

  @GetMapping("/statistics/all")
  public StatisticsResponse allStatistics() {
    return statisticService.getAllStatistics();
  }
}
