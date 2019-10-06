package com.github.luksrn.tdcrec2019.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.luksrn.tdcrec2019.dominio.Assignment;
import com.github.luksrn.tdcrec2019.dominio.Course;
import com.github.luksrn.tdcrec2019.dominio.dto.CreateAssignmentRequest;
import com.github.luksrn.tdcrec2019.service.AssignmentService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;


import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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

    @Test
    public void testAssignmentCreation() throws Exception{

        // Given
        CreateAssignmentRequest payload = new CreateAssignmentRequest(
                "Spring Application Events", "TDC talk about Spring events", Instant.now());

        //when
        mvc.perform(post("/course/{courseId}/assignment/create", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload))
                ).andExpect(status().isCreated());

        // then
        Course course = new Course();
        course.setId(1L);
        List<Assignment> assignments = assignmentService.findAllByCourse(course);
        assertThat(assignments)
                .hasSize(1)
                .flatExtracting(Assignment::getTitle, Assignment::getInstructions)
                .contains("Spring Application Events", "TDC talk about Spring events");

    }
}
