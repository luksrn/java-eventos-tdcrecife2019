package com.github.luksrn.tdcrec2019.controller.hetoas;


import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import com.github.luksrn.tdcrec2019.controller.AssignmentController;
import com.github.luksrn.tdcrec2019.dominio.Assignment;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
public class AssignmentResourceAssembler implements ResourceAssembler<Assignment, Resource<Assignment>> {

    @Override
    public Resource<Assignment> toResource(Assignment assignment) {
        return new Resource<>(assignment,
                linkTo(methodOn(AssignmentController.class).createAssignment(null, null)).withRel("create"),
                linkTo(methodOn(AssignmentController.class).view(assignment.getCourse().getId(), assignment.getId())).withSelfRel());
    }
}
