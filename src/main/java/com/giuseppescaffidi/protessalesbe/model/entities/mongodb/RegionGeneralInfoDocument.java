package com.giuseppescaffidi.protessalesbe.model.entities.mongodb;

import lombok.Data;

@Data
public class RegionGeneralInfoDocument {
        private String typeOfFormulary;
        private String inclusionProcess;
        private int averageTimeForCommissionMeeting;
        private String treatmentPlanAndPrescriptionForm;
        private String centralPurchasingBody;
        private String prescriptionSystem;
}
