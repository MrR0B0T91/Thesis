package main.model;

import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Users {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "is_moderator")
  private boolean isModerator;

  @Column(name = "reg_time")
  private Date regTime;

  private String name;
  private String email;
  private String password;
  private String code;
  private String text;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<Posts> posts;

  @ManyToMany(mappedBy = "usersVoteList", fetch = FetchType.LAZY)
  private List<Posts> postsWithVotes;

  @ManyToMany(mappedBy = "postCommentsList", fetch = FetchType.LAZY)
  private List<Posts> postsWithComments;
}
