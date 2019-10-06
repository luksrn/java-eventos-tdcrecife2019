package com.github.luksrn.tdcrec2019.dominio.dto;

import lombok.Value;

import javax.validation.constraints.NotEmpty;
import java.time.Instant;

@Value
public final class CreateAssignmentRequest {
    @NotEmpty
    private String title;
    private String instructions;
    private Instant due;
}
