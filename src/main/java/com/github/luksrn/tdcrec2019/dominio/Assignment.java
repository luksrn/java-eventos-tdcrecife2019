package com.github.luksrn.tdcrec2019.dominio;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;

@Entity
@Data
public class Assignment {
    @Id @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String title;
    private String instructions;
    private Instant due;
    @ManyToOne @JoinTable(name = "course_id")
    private Course course;

    Assignment(){}

    public Assignment(@NotEmpty String title, String instructions, Instant due) {
        this.title = title;
        this.instructions = instructions;
        this.due = due;
    }
}

