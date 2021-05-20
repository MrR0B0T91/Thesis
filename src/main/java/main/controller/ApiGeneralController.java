package main.controller;

import java.security.Principal;
import javax.servlet.http.HttpServletRequest;
import main.api.requset.CommentRequest;
import main.api.requset.ModerateRequest;
import main.api.requset.ProfileRequest;
import main.api.response.CalendarResponse;
import main.api.response.CommentResponse;
import main.api.response.InitResponse;
import main.api.response.LikeDislikeResponse;
import main.api.response.ProfileResponse;
import main.api.response.SettingsResponse;
import main.api.response.StatisticsResponse;
import main.api.response.TagResponse;
import main.service.PostService;
import main.service.ProfileService;
import main.service.SettingsService;
import main.service.StatisticService;
import main.service.TagService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiGeneralController {

  private final InitResponse initResponse;
  private final SettingsService settingsService;
  private final TagService tagService;
  private final PostService postService;
  private final StatisticService statisticService;
  private final ProfileService profileService;

  public ApiGeneralController(
      InitResponse initResponse, SettingsService settingsService, TagService tagService,
      PostService postService, StatisticService statisticService,
      ProfileService profileService) {
    this.initResponse = initResponse;
    this.settingsService = settingsService;
    this.tagService = tagService;
    this.postService = postService;
    this.statisticService = statisticService;
    this.profileService = profileService;
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

  @PostMapping(value = "/profile/my", consumes = {"multipart/form-data"})
  @PreAuthorize("hasAuthority('user:write')")
  public ProfileResponse multipartProfile(@RequestPart("photo") byte[] photo,
      @RequestParam(value = "name") String name,
      @RequestParam(value = "email") String email,
      @RequestParam(value = "removePhoto", defaultValue = "0") int removePhoto,
      @RequestParam(value = "password") String password,
      HttpServletRequest httpServletRequest, Principal principal) {
    return profileService
        .multipartProfile(photo, name, email, removePhoto, password, httpServletRequest, principal);
  }

  @PostMapping(value = "/profile/my", consumes = {"application/json"})
  @PreAuthorize("hasAuthority('user:write')")
  public ProfileResponse jsonProfile(@RequestBody ProfileRequest profileRequest) {
    return profileService.jsonProfile(profileRequest);
  }

  @PostMapping("moderation")
  @PreAuthorize("hasAuthority('user:moderate')")
  public LikeDislikeResponse moderatePost(@RequestBody ModerateRequest moderateRequest,
      Principal principal) {
    return postService.moderatePost(moderateRequest, principal);
  }
}
