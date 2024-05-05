package com.giuseppescaffidi.protessalesbe.model.entities.mongodb;

import com.giuseppescaffidi.protessalesbe.model.dtos.OutletDTO;
import com.giuseppescaffidi.protessalesbe.model.dtos.RegionGeneralInfoDTO;
import lombok.Data;

import java.util.List;

@Data
public class RegionDocument {
    private int regionId;
    private String name;
    private double potential;
    private double heatmapInclusionProcess;
    private RegionGeneralInfoDocument generalInfo;
    private List<OutletDocument> outlets;
    private InclusionProcessDocument inclusionProcess;
    private List<ActivityPlanDocument> activityPlan;

    public int getTotalPatient() {
        return this.outlets.stream().map(OutletDocument::getPotentialNumber).reduce(0, Integer::sum);
    }

    public void addAllActivityPlan(List<ActivityPlanDocument> activityPlan) {
        List<Integer> activityIds = this.activityPlan.stream().map(ActivityPlanDocument::getActivityId).toList();
        activityPlan.forEach(activityPlanDocument -> {
            if (!activityIds.contains(activityPlanDocument.getActivityId())) {
                this.activityPlan.add(activityPlanDocument);
            }
        });
    }

    public void setGeneralInfo(RegionGeneralInfoDTO regionGeneralInfoDTO) {
        RegionGeneralInfoDocument regionGeneralInfoDocument = new RegionGeneralInfoDocument();

        regionGeneralInfoDocument.setInclusionProcess(regionGeneralInfoDTO.getInclusionProcess());
        regionGeneralInfoDocument.setCentralPurchasingBody(regionGeneralInfoDTO.getCentralPurchasingBody());
        regionGeneralInfoDocument.setPrescriptionSystem(regionGeneralInfoDTO.getPrescriptionSystem());
        regionGeneralInfoDocument.setTypeOfFormulary(regionGeneralInfoDTO.getTypeOfFormulary());
        regionGeneralInfoDocument.setAverageTimeForCommissionMeeting(regionGeneralInfoDTO
                .getAverageTimeForCommissionMeeting());
        regionGeneralInfoDocument.setTreatmentPlanAndPrescriptionForm(regionGeneralInfoDTO
                .getTreatmentPlanAndPrescriptionForm());

        this.generalInfo = regionGeneralInfoDocument;
    }

    public void upsertOutlet(OutletDTO outletDTO) {
        OutletDocument outletDocument = new OutletDocument(outletDTO);

        if (outletExistInList(outletDocument)) {
            this.outlets.remove(this.getOutletById(outletDTO.getId()));
        }

        this.outlets.add(outletDocument);
    }

    public boolean outletExistInList(OutletDocument outletDocument) {
        return this.outlets.stream().map(OutletDocument::getId).toList()
                .contains(outletDocument.getId());
    }

    public OutletDocument getOutletById(String id) {
        return this.outlets.stream().filter(outletDocument -> outletDocument.getId().equals(id)).toList()
                .get(0);
    }
}
