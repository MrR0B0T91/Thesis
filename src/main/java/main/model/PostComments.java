package main.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "post_comments")
public class PostComments {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @Column(name = "parent_id", columnDefinition = "INT")
  private Integer parentId;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(name = "post_id", nullable = false, columnDefinition = "INT")
  private int postId;

  @Column(nullable = false, columnDefinition = "DATETIME")
  private Date time;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String text;
}
