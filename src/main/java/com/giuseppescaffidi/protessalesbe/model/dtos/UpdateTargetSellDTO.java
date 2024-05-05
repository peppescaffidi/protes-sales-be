package com.giuseppescaffidi.protessalesbe.model.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UpdateTargetSellDTO extends UpdateTargetCoverageDTO {
    private String outletId;
}
