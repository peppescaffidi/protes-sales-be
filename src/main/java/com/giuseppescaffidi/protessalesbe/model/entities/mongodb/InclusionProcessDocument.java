package com.giuseppescaffidi.protessalesbe.model.entities.mongodb;

import com.giuseppescaffidi.protessalesbe.model.dtos.CoverageProcessStepDTO;
import lombok.Data;

import java.util.List;

@Data
public class InclusionProcessDocument {
    private String image;
    private List<CoverageProcessStepDocument> steps;

    public void updateStep(CoverageProcessStepDTO inclusionProcessStepDTO) {
        // Remove old step
        this.steps.removeIf(step -> step.getStepNumber() == inclusionProcessStepDTO.getStepNumber());

        // Add new step
        this.steps.add(new CoverageProcessStepDocument(inclusionProcessStepDTO));

        // Sorting list by step number
        this.steps.sort((o1, o2) -> (o1.getStepNumber() - o2.getStepNumber()));
    }
}
