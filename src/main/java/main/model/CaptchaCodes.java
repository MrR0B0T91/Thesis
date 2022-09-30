package main.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Calendar;

@Data
@Entity
@Table(name = "captcha_codes")
public class CaptchaCodes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, columnDefinition = "DATETIME")
    private Calendar time;

    @Column(nullable = false, columnDefinition = "TINYTEXT")
    private String code;

    @Column(name = "secret_code", nullable = false, columnDefinition = "TINYTEXT")
    private String secretCode;
}
