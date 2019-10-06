package com.github.luksrn.tdcrec2019.dominio.event;

import com.github.luksrn.tdcrec2019.dominio.Assignment;
import lombok.Value;

@Value
public final class AssignmentCreated {
    private Assignment assignment;
}
