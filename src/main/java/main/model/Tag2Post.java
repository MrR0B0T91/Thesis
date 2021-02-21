package main.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tag2post")
public class Tag2Post {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @Column(name = "post_id", nullable = false, columnDefinition = "INT")
  private int postId;

  @Column(name = "tag_id", nullable = false, columnDefinition = "INT")
  private int tagId;
}
