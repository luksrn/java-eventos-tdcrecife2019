package com.github.luksrn.tdcrec2019.controller;

import com.github.luksrn.tdcrec2019.controller.hetoas.AssignmentResourceAssembler;
import com.github.luksrn.tdcrec2019.dominio.Assignment;
import com.github.luksrn.tdcrec2019.dominio.Course;
import com.github.luksrn.tdcrec2019.dominio.dto.CreateAssignmentRequest;
import com.github.luksrn.tdcrec2019.repository.CourseRepository;
import com.github.luksrn.tdcrec2019.service.AssignmentService;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RestController
@AllArgsConstructor
public class AssignmentController {

    private AssignmentResourceAssembler assembler;
    private AssignmentService assignmentService;
    private ConversionService conversionService;

    @PostMapping("/course/{courseId}/assignment/create")
    public ResponseEntity<?> createAssignment(@PathVariable("courseId") Course course,
                                              @Valid @RequestBody CreateAssignmentRequest request){
        try {
            Assignment assignment = assignmentService.create(course,conversionService.convert(request, Assignment.class));
            Resource<Assignment> resource = assembler.toResource(assignment);
            return ResponseEntity.created(URI.create(resource.getId().expand().getHref())).build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PostMapping("/course/{courseId}/assignment/{assignmentId}/view")
    public ResponseEntity<?> view(@PathVariable("courseId") Long courseId,
                                  @PathVariable("assignmentId") Long assignmentId){
        // Ignored because it's outside of scope
        return ResponseEntity.ok().build();
    }
}
