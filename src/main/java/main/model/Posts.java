package main.model;

import java.util.Calendar;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "posts")
public class Posts {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "is_active", nullable = false, columnDefinition = "TINYINT")
  private int isActive;

  @Enumerated(EnumType.STRING)
  @Column(columnDefinition = "enum")
  private ModerationStatus moderationStatus = ModerationStatus.NEW;

  @Column(name = "moderator_id", columnDefinition = "INT")
  private Integer moderatorId;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(nullable = false, columnDefinition = "DATETIME")
  private Calendar time;

  @Column(nullable = false, columnDefinition = "VARCHAR(255)")
  private String title;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String text;

  @Column(name = "view_count", nullable = false, columnDefinition = "INT")
  private int viewCount;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(
      name = "post_votes",
      joinColumns = {@JoinColumn(name = "post_id")},
      inverseJoinColumns = {@JoinColumn(name = "user_id")})
  private List<PostVotes> postVoteList;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "post_comments",
      joinColumns = {@JoinColumn(name = "post_id")},
      inverseJoinColumns = {@JoinColumn(name = "user_id")}
  )
  private List<PostComments> postCommentsList;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(
      name = "tag2post",
      joinColumns = {@JoinColumn(name = "post_id")},
      inverseJoinColumns = {@JoinColumn(name = "tag_id")})
  private List<Tags> tagsList;
}
