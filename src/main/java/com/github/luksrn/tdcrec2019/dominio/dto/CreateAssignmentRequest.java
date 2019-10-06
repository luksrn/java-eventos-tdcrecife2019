package com.github.luksrn.tdcrec2019.dominio.dto;

import lombok.Value;

import java.time.Instant;

@Value
public final class CreateAssignmentRequest {
    private String title;
    private String instructions;
    private Instant due;
}
