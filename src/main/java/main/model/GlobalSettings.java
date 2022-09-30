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
@Table(name = "global_settings")
public class GlobalSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false, columnDefinition = "VARCHAR(255)")
    private String code;

    @Column(nullable = false, columnDefinition = "VARCHAR(255)")
    private String name;

    @Column(nullable = false, columnDefinition = "VARCHAR(255)")
    private String value;

}
