package com.giuseppescaffidi.protessalesbe.model.dtos;

import com.giuseppescaffidi.protessalesbe.model.entities.mongodb.RegionGeneralInfoDocument;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RegionGeneralInfoDTO extends RegionGeneralInfoDocument {
    public RegionGeneralInfoDTO() {}

    public RegionGeneralInfoDTO(RegionGeneralInfoDocument regionGeneralInfoDocument) {
        this.setTypeOfFormulary(regionGeneralInfoDocument.getTypeOfFormulary());
        this.setInclusionProcess(regionGeneralInfoDocument.getInclusionProcess());
        this.setAverageTimeForCommissionMeeting(regionGeneralInfoDocument.getAverageTimeForCommissionMeeting());
        this.setTreatmentPlanAndPrescriptionForm(regionGeneralInfoDocument.getTreatmentPlanAndPrescriptionForm());
        this.setCentralPurchasingBody(regionGeneralInfoDocument.getCentralPurchasingBody());
        this.setPrescriptionSystem(regionGeneralInfoDocument.getPrescriptionSystem());
    }
}
