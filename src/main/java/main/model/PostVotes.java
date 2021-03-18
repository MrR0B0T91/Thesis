package main.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "post_votes")
public class PostVotes {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "user_id", nullable = false, columnDefinition = "INT")
  private int userId;

  @Column(name = "post_id", nullable = false, columnDefinition = "INT")
  private int postId;

  @Column(nullable = false, columnDefinition = "DATETIME")
  private Date time;

  @Column(nullable = false, columnDefinition = "TINYINT")
  private boolean value;
}
