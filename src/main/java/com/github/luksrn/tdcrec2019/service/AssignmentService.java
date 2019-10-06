package com.github.luksrn.tdcrec2019.service;

import com.github.luksrn.tdcrec2019.dominio.Assignment;
import com.github.luksrn.tdcrec2019.dominio.Course;
import com.github.luksrn.tdcrec2019.dominio.Email;
import com.github.luksrn.tdcrec2019.dominio.Student;
import com.github.luksrn.tdcrec2019.repository.AssignmentRepository;
import com.github.luksrn.tdcrec2019.repository.CourseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class AssignmentService {

    private AssignmentRepository assignmentRepository;
    private EmailService emailService;
    private CourseRepository courseRepository;

    @Transactional
    public Assignment create(final Course course, final Assignment assignment){
        // Any other validations,action here...
        assignment.setCourse(course);
        assignmentRepository.save(assignment);

        // course#getStudents is Lazy, so...
        final Set<Student> students = courseRepository.findStudents(course);
        students.forEach( student ->  {
            final Email email = new Email(student.getEmail(),
                    "New assignment created '" + assignment.getTitle() +"'",
                            assignment.getInstructions());
            emailService.send(email);
        });

        return assignment;
    }

    public List<Assignment> findAllByCourse(final Course course){
        return assignmentRepository.findAllByCourse(course);
    }
}
