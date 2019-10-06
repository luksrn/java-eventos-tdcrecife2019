package com.github.luksrn.tdcrec2019.service;

import com.github.luksrn.tdcrec2019.dominio.Assignment;
import com.github.luksrn.tdcrec2019.dominio.Course;
import com.github.luksrn.tdcrec2019.dominio.Email;
import com.github.luksrn.tdcrec2019.dominio.Student;
import com.github.luksrn.tdcrec2019.dominio.event.AssignmentCreated;
import com.github.luksrn.tdcrec2019.repository.AssignmentRepository;
import com.github.luksrn.tdcrec2019.repository.CourseRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class AssignmentService {

    private AssignmentRepository assignmentRepository;
    private ApplicationEventPublisher publisher;

    @Transactional
    public Assignment create(final Course course, final Assignment assignment){
        // Any other validations,action here...
        assignment.setCourse(course);
        assignmentRepository.save(assignment);

        publisher.publishEvent(new AssignmentCreated(assignment));

        return assignment;
    }

    public List<Assignment> findAllByCourse(final Course course){
        return assignmentRepository.findAllByCourse(course);
    }
}
