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
@Table(name = "post_votes")
public class PostVotes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id", nullable = false, columnDefinition = "INT")
    private int userId;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Posts post;

    @Column(nullable = false, columnDefinition = "DATETIME")
    private Date time;

    @Column(nullable = false, columnDefinition = "TINYINT")
    private int value;
}
