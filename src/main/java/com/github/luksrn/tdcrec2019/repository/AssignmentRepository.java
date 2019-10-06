package com.github.luksrn.tdcrec2019.repository;

import com.github.luksrn.tdcrec2019.dominio.Assignment;
import com.github.luksrn.tdcrec2019.dominio.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findAllByCourse(Course course);
}
