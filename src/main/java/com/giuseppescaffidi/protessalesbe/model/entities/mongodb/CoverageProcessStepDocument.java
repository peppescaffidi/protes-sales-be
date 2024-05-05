package com.giuseppescaffidi.protessalesbe.model.entities.mongodb;

import com.giuseppescaffidi.protessalesbe.model.dtos.CoverageProcessStepDTO;
import lombok.Data;

import java.util.List;

@Data
public class CoverageProcessStepDocument {
    private int stepNumber;
    private String name;
    private String status;
    private String description;
    private int nextStep;
    private boolean isFirstStep;
    private String dueDate;
    private String endDate;
    private ConditionalStepDocument conditional;
    private List<OutletCoverageProcessStepDocument> outlets;
    private boolean isLastStep;
    private boolean isLocal;
    private boolean isCoverage;
    private boolean isSell;

    public CoverageProcessStepDocument() {}

    public CoverageProcessStepDocument(CoverageProcessStepDTO inclusionProcessStepDTO) {
        this.stepNumber = inclusionProcessStepDTO.getStepNumber();
        this.name = inclusionProcessStepDTO.getName();
        this.status = inclusionProcessStepDTO.getStatus();
        this.description = inclusionProcessStepDTO.getDescription();
        this.nextStep = inclusionProcessStepDTO.getNextStep();
        this.isFirstStep = inclusionProcessStepDTO.isFirstStep();
        this.dueDate = inclusionProcessStepDTO.getDueDate();
        this.endDate = inclusionProcessStepDTO.getEndDate();
        this.conditional = inclusionProcessStepDTO.getConditional();
        this.outlets = inclusionProcessStepDTO.getOutlets();
        this.isLastStep = inclusionProcessStepDTO.isLastStep();
        this.isLocal = inclusionProcessStepDTO.isLocal();
        this.isCoverage = inclusionProcessStepDTO.isCoverage();
        this.isSell = inclusionProcessStepDTO.isSell();
    }
}
