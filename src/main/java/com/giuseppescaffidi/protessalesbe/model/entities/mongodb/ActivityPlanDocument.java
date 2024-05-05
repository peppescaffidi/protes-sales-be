package com.giuseppescaffidi.protessalesbe.model.entities.mongodb;

import com.giuseppescaffidi.protessalesbe.model.dtos.ActivityPlanDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class ActivityPlanDocument {
    private Integer activityId;
    private String name;
    private List<Integer> inclusionStepsInvolved;
    private String expectedDate;
    private String criticalIssue;
    private String owner;
    private String stakeholder;
    private List<NoteDocument> notes;
    private String status;
    private boolean isNational;

    public ActivityPlanDocument() {};

    public ActivityPlanDocument(ActivityPlanDTO activityPlanDTO) {
        this.setActivityId(activityPlanDTO.getActivityId());
        this.setName(activityPlanDTO.getName());

        if (activityPlanDTO.getInclusionStepsInvolved() != null) {
            List<Integer> inclusionStepsInvolved = new ArrayList<>();
            if (activityPlanDTO.getInclusionStepsInvolved().contains(",")) {
                inclusionStepsInvolved = Arrays.stream(activityPlanDTO.getInclusionStepsInvolved()
                        .replaceAll("\\s", "").split(",")).map(Integer::parseInt).toList();
            } else {
                inclusionStepsInvolved.add(Integer.parseInt(activityPlanDTO.getInclusionStepsInvolved()));
            }
            this.setInclusionStepsInvolved(inclusionStepsInvolved);
        }

        this.setExpectedDate(activityPlanDTO.getExpectedDate());
        this.setCriticalIssue(activityPlanDTO.getCriticalIssue());
        this.setOwner(activityPlanDTO.getOwner());
        this.setStakeholder(activityPlanDTO.getStakeholder());
        this.setNotes(activityPlanDTO.getNotes());
        this.setStatus(activityPlanDTO.getStatus());
        this.setNational(activityPlanDTO.isNational());
    }
}
