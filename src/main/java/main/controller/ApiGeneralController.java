package main.controller;

import main.api.response.CalendarResponse;
import main.api.response.InitResponse;
import main.api.response.SettingsResponse;
import main.api.response.TagResponse;
import main.service.PostService;
import main.service.SettingsService;
import main.service.TagService;
import org.springframework.web.bind.annotation.GetMapping;
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

  public ApiGeneralController(
      InitResponse initResponse, SettingsService settingsService, TagService tagService,
      PostService postService) {
    this.initResponse = initResponse;
    this.settingsService = settingsService;
    this.tagService = tagService;
    this.postService = postService;
  }

  @GetMapping("/init")
  private InitResponse init() {
    return initResponse;
  }

  @GetMapping("/settings")
  private SettingsResponse settings() {
    return settingsService.getGlobalSettings();
  }

  @GetMapping("/tag")
  private TagResponse tags(
      @RequestParam(value = "query", defaultValue = "") String name) {
    return tagService.getTags(name);
  }

  @GetMapping("/calendar")
  private CalendarResponse postsByYear(
      @RequestParam(value = "year", defaultValue = "") String year) {
    return postService.getPostsByYear(year);
  }
}
