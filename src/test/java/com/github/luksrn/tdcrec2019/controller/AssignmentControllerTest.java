package com.github.luksrn.tdcrec2019.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.luksrn.tdcrec2019.dominio.Assignment;
import com.github.luksrn.tdcrec2019.dominio.Course;
import com.github.luksrn.tdcrec2019.dominio.Email;
import com.github.luksrn.tdcrec2019.dominio.dto.CreateAssignmentRequest;
import com.github.luksrn.tdcrec2019.service.AssignmentService;
import com.github.luksrn.tdcrec2019.service.EmailService;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;


import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mockingDetails;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AssignmentControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AssignmentService assignmentService;

    @MockBean
    private EmailService emailService;

    @Captor
    private ArgumentCaptor<Email> emailCaptor;

    @Test
    public void testAssignmentCreation() throws Exception {

        // Given
        doNothing().when(emailService).send(emailCaptor.capture());

        Email emailToBenjamin = new Email("benjamin@email.com", "New assignment created 'Spring Application Events'", "TDC talk about Spring event");
        Email emailToZooey = new Email("zoey@email.com", "New assignment created 'Spring Application Events'", "TDC talk about Spring event");

        CreateAssignmentRequest payload = new CreateAssignmentRequest(
                "Spring Application Events", "TDC talk about Spring event", Instant.now());

        //when
        MvcResult mvcResult = mvc.perform(post("/course/{courseId}/assignment/create", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payload))
        ).andReturn();

        // then
        SoftAssertions softly = new SoftAssertions();

        softly.assertThat(mvcResult.getResponse().getStatus())
                .isEqualTo(HttpStatus.CREATED.value());

        List<Assignment> assignments = assignmentService.findAllByCourse(new Course(1L));
        softly.assertThat(assignments)
                .hasSize(1)
                .flatExtracting(Assignment::getTitle, Assignment::getInstructions)
                .contains("Spring Application Events", "TDC talk about Spring event");

        softly.assertThat(mockingDetails(emailService).getInvocations().size()).isEqualTo(2);
        softly.assertThat(emailCaptor.getAllValues())
                .hasSize(2)
                .containsExactlyInAnyOrder(emailToBenjamin, emailToZooey);

        softly.assertAll();
    }


    @Test
    public void testThatAssignmentCreationShouldNotSendEmailWhenFailure() throws Exception {

        // Given
        doNothing().when(emailService).send(emailCaptor.capture());
        CreateAssignmentRequest payload = new CreateAssignmentRequest(
                null, null, null);

        //when
        MvcResult mvcResult = mvc.perform(post("/course/{courseId}/assignment/create", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payload))
        ).andReturn();

        // then
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(mvcResult.getResponse().getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
        softly.assertThat(assignmentService.findAllByCourse(new Course(1L))).isEmpty();
        softly.assertThat(mockingDetails(emailService).getInvocations()).as("Shouldn't interact with EmailService").isEmpty();
        softly.assertAll();
    }
}
