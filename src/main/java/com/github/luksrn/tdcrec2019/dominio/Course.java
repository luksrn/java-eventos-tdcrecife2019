package com.github.luksrn.tdcrec2019.dominio;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity
@Data
@NamedEntityGraph(name = "Course.students",
        attributeNodes = @NamedAttributeNode("students"))
public class Course {
    @Id
    @GeneratedValue
    private Long id;
    @NotEmpty
    private String title;

    @OneToMany
    @JoinTable(name="enrollment",
            joinColumns = @JoinColumn( name="course_id"),
            inverseJoinColumns = @JoinColumn( name="student_id"))
    private Set<Student> students;

}
