package com.giuseppescaffidi.protessalesbe.model.dtos;

import com.giuseppescaffidi.protessalesbe.model.entities.mongodb.ActivityPlanDocument;
import com.giuseppescaffidi.protessalesbe.model.entities.mongodb.NoteDocument;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ActivityPlanDTO {
    private Integer activityId;
    private String name;
    private String inclusionStepsInvolved;
    private String expectedDate;
    private String criticalIssue;
    private String owner;
    private String stakeholder;
    private List<NoteDocument> notes;
    private String status;
    private boolean isNational;
    public static List<ActivityPlanDTO> getActivityPlanDTOListFromActivityPlanDocumentList(List<ActivityPlanDocument>
                                                                                            activityPlanDocuments) {
        List<ActivityPlanDTO> activityPlanDTOS = new ArrayList<>();

        if (activityPlanDocuments != null) {
            activityPlanDocuments.forEach(activityPlanDocument -> {
                ActivityPlanDTO activityPlanDTO = new ActivityPlanDTO();
                activityPlanDTO.setActivityId(activityPlanDocument.getActivityId());
                activityPlanDTO.setName(activityPlanDocument.getName());
                activityPlanDTO.setNational(activityPlanDocument.isNational());
                activityPlanDTO.setNotes(activityPlanDocument.getNotes());
                activityPlanDTO.setOwner(activityPlanDocument.getOwner());
                activityPlanDTO.setCriticalIssue(activityPlanDocument.getCriticalIssue());
                activityPlanDTO.setExpectedDate(activityPlanDocument.getExpectedDate());

                if (activityPlanDocument.getInclusionStepsInvolved() != null) {
                    String inclusionStepToString = activityPlanDocument.getInclusionStepsInvolved()
                            .toString().replaceAll("\\s", "");

                    activityPlanDTO.setInclusionStepsInvolved(inclusionStepToString
                            .substring(inclusionStepToString.indexOf('[') + 1, inclusionStepToString.indexOf(']')));
                }

                activityPlanDTO.setStakeholder(activityPlanDocument.getStakeholder());
                activityPlanDTO.setStatus(activityPlanDocument.getStatus());

                activityPlanDTOS.add(activityPlanDTO);
            });
        } else {
            ActivityPlanDTO activityPlanDTO = new ActivityPlanDTO();
            activityPlanDTO.setName("No activity found");
            activityPlanDTOS.add(activityPlanDTO);
        }

        return activityPlanDTOS;
    }
}
