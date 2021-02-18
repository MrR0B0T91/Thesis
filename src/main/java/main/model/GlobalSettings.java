package main.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "global_settings")
public class GlobalSettings {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  private String code;
  private String name;
  private String value;
  /*
   * MULTIUSER_MODE
   * POST_PREMODERATION
   * STATISTICS_IS_PUBLIC
   */
}
