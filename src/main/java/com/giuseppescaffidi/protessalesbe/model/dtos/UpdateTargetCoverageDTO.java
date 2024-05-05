package com.giuseppescaffidi.protessalesbe.model.dtos;

import lombok.Data;

@Data
public class UpdateTargetCoverageDTO {
    private Integer productId;
    private Integer countryId;
    private Integer regionId;
    private String oldDueDate;
    private String dueDate;
}
