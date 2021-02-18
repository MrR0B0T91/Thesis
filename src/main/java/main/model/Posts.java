package main.model;

import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import lombok.Data;
import org.hibernate.annotations.Cascade;

@Data
@Entity
public class Posts {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "is_active")
  private boolean isActive;

  @Enumerated(EnumType.STRING)
  @Column(columnDefinition = "enum")
  private ModerationStatus moderationStatus = ModerationStatus.NEW;

  @Column(name = "moderator_id")
  private boolean moderatorId;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private Users user;

  private Date time;
  private String title;
  private String text;

  @Column(name = "view_count")
  private int viewCount;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "post_votes",
      joinColumns = {@JoinColumn(name = "post_id")},
      inverseJoinColumns = {@JoinColumn(name = "user_id")}
  )
  private List<Users> usersVoteList;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "post_comments",
      joinColumns = {@JoinColumn(name = "post_id")},
      inverseJoinColumns = {@JoinColumn(name = "user_id")}
  )
  private List<PostComments> postCommentsList;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "tag2post",
      joinColumns = {@JoinColumn(name = "post_id")},
      inverseJoinColumns = {@JoinColumn(name = "tag_id")}
  )
  private List<Tags> tagsList;
}
