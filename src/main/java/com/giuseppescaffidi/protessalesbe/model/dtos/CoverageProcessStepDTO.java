package com.giuseppescaffidi.protessalesbe.model.dtos;

import com.giuseppescaffidi.protessalesbe.model.entities.mongodb.OutletCoverageProcessStepDocument;
import com.giuseppescaffidi.protessalesbe.model.entities.mongodb.CoverageProcessStepDocument;

public class CoverageProcessStepDTO extends CoverageProcessStepDocument {
    public CoverageProcessStepDTO() {}

    public CoverageProcessStepDTO(CoverageProcessStepDocument coverageProcessStepDocument) {
        this.setName(coverageProcessStepDocument.getName());
        this.setFirstStep(coverageProcessStepDocument.isFirstStep());
        this.setNextStep(coverageProcessStepDocument.getNextStep());
        this.setStepNumber(coverageProcessStepDocument.getStepNumber());
        this.setDescription(coverageProcessStepDocument.getDescription());
        this.setConditional(coverageProcessStepDocument.getConditional());
        this.setStatus(coverageProcessStepDocument.getStatus());
        this.setDueDate(coverageProcessStepDocument.getDueDate());
        this.setEndDate(coverageProcessStepDocument.getEndDate());
        this.setOutlets(coverageProcessStepDocument.getOutlets());
        this.setLastStep(coverageProcessStepDocument.isLastStep());
        this.setLocal(coverageProcessStepDocument.isLocal());
        this.setCoverage(coverageProcessStepDocument.isCoverage());
        this.setSell(coverageProcessStepDocument.isSell());
    }

    public void updateLocalOutletStep(String outletOnekey, CoverageProcessStepDocument coverageProcessStepDocument) {
        OutletCoverageProcessStepDocument outletInclusionProcessStepDocument = this.getOutlets().stream()
                .filter(outlet -> outlet.getId().equals(outletOnekey)).toList().get(0);

        outletInclusionProcessStepDocument.setDueDate(coverageProcessStepDocument.getDueDate());
        outletInclusionProcessStepDocument.setStatus(coverageProcessStepDocument.getStatus());
    }
}
