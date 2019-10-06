package com.github.luksrn.tdcrec2019.repository;

import com.github.luksrn.tdcrec2019.dominio.Course;
import com.github.luksrn.tdcrec2019.dominio.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query("select s from Course c join c.students s where c = :course")
    Set<Student> findStudents(@Param("course") Course course);
}
