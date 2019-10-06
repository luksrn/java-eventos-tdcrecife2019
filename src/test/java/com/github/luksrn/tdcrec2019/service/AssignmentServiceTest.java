package com.github.luksrn.tdcrec2019.service;

import com.github.luksrn.tdcrec2019.dominio.Assignment;
import com.github.luksrn.tdcrec2019.dominio.Course;
import com.github.luksrn.tdcrec2019.dominio.Email;
import com.github.luksrn.tdcrec2019.repository.CourseRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AssignmentServiceTest {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private AssignmentService assignmentService;

    @MockBean
    private EmailService emailService;

    @Captor
    private ArgumentCaptor<Email> emailCaptor;

    @Test
    public void shouldCreateAnAssignmentAndSentAnEmail(){
        // given
        Course springCourse = courseRepository.findById(1L).get();
        Assignment assignment = new Assignment("Title", "Description", Instant.now());
        Email emailToBenjamin = new Email("benjamin@email.com", "New assignment created 'Title'", "Description");
        Email emailToZooey = new Email("zoey@email.com", "New assignment created 'Title'", "Description");

        doNothing().when(emailService).send(emailCaptor.capture());

        // when
        assignmentService.create(springCourse, assignment);

        // then
        assertThat(assignment.getId()).isNotNull();
        assertThat(assignment.getCourse()).isEqualTo(springCourse);

        verify(emailService, times(2)).send(any());

        List<Email> emailsSent = emailCaptor.getAllValues();
        assertThat(emailsSent)
                .hasSize(2)
                .containsExactlyInAnyOrder(emailToBenjamin, emailToZooey);
    }
}
