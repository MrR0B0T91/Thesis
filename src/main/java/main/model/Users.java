package main.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class Users {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  private int id;

  @Column(name = "is_moderator", nullable = false, columnDefinition = "TINYINT")
  private boolean isModerator;

  @Column(name = "reg_time", nullable = false, columnDefinition = "DATETIME")
  private Date regTime;

  @Column(nullable = false, columnDefinition = "VARCHAR(255)")
  private String name;

  @Column(nullable = false, columnDefinition = "VARCHAR(255)")
  private String email;

  @Column(nullable = false, columnDefinition = "VARCHAR(255)")
  private String password;

  @Column(columnDefinition = "VARCHAR(255)")
  private String code;

  @Column(columnDefinition = "TEXT")
  private String photo;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<Posts> posts;

  @ManyToMany(mappedBy = "postCommentsList", fetch = FetchType.LAZY)
  private List<Posts> postsWithComments;
}
