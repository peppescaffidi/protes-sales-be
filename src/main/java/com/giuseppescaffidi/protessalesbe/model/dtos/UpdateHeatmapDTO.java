package com.giuseppescaffidi.protessalesbe.model.dtos;

import lombok.Data;

@Data
public class UpdateHeatmapDTO {
    private Integer productId;
    private Integer countryId;
    private Integer regionId;
    private String outletId;
    private String oldStepStatus;
    private String newStepStatus;
}
