package com.github.luksrn.tdcrec2019.conversion;

import com.github.luksrn.tdcrec2019.dominio.Assignment;
import com.github.luksrn.tdcrec2019.dominio.dto.CreateAssignmentRequest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class CreateAssignmentRequestToAssignmentConverter implements Converter<CreateAssignmentRequest, Assignment> {

    @Override
    public Assignment convert(CreateAssignmentRequest source) {
        return new Assignment( source.getTitle(), source.getInstructions(), source.getDue());
    }
}
