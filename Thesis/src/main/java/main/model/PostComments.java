package main.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "post_comments")
public class PostComments {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @Column(name = "parent_id")
  private int parentId;

  @Column(name = "user_id")
  private int userId;

  @Column(name = "post_id")
  private int postId;

  private Date time;
  private String text;
}
