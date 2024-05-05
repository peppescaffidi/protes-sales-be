package com.giuseppescaffidi.protessalesbe.model.entities.mongodb;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class OutletCoverageProcessStepDocument extends OutletDocument {
    private String status;
    private String dueDate;
}
