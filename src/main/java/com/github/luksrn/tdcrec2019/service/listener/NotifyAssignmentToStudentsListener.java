package com.github.luksrn.tdcrec2019.service.listener;

import com.github.luksrn.tdcrec2019.dominio.Assignment;
import com.github.luksrn.tdcrec2019.dominio.Email;
import com.github.luksrn.tdcrec2019.dominio.Student;
import com.github.luksrn.tdcrec2019.dominio.event.AssignmentCreated;
import com.github.luksrn.tdcrec2019.repository.CourseRepository;
import com.github.luksrn.tdcrec2019.service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Set;

@Component
@AllArgsConstructor
public class NotifyAssignmentToStudentsListener {

    private EmailService emailService;
    private CourseRepository courseRepository;

    @TransactionalEventListener
    public void handle(AssignmentCreated event){
        Assignment assignment = event.getAssignment();
        // course#getStudents is Lazy, so...
        final Set<Student> students = courseRepository.findStudents(assignment.getCourse());
        students.forEach( student ->  {
            final Email email = new Email(student.getEmail(),
                    "New assignment created '" + assignment.getTitle() +"'",
                    assignment.getInstructions());
            emailService.send(email);
        });
    }
}
