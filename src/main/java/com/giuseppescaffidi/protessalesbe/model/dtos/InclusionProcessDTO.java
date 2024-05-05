package com.giuseppescaffidi.protessalesbe.model.dtos;

import com.giuseppescaffidi.protessalesbe.model.entities.mongodb.InclusionProcessDocument;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InclusionProcessDTO {
    private String image;
    private List<CoverageProcessStepDTO> steps;

    public InclusionProcessDTO(InclusionProcessDocument inclusionProcess) {
        this.setImage(inclusionProcess.getImage());
        this.setSteps(inclusionProcess.getSteps().stream().map(CoverageProcessStepDTO::new).toList());
    }
}
