package com.giuseppescaffidi.protessalesbe.model.dtos;

import com.giuseppescaffidi.protessalesbe.model.entities.mongodb.OutletDocument;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class OutletDTO extends OutletDocument {
    public OutletDTO() {}

    public OutletDTO(OutletDocument outletDocument) {
        this.setId(outletDocument.getId());
        this.setCluster(outletDocument.getCluster());
        this.setName(outletDocument.getName());
        this.setCity(outletDocument.getCity());
        this.setProvince(outletDocument.getProvince());
        this.setAverageTimeForCommissionMeeting(outletDocument.getAverageTimeForCommissionMeeting());
        this.setPotentialNumber(outletDocument.getPotentialNumber());
        this.setPotentialPercentage(outletDocument.getPotentialPercentage());
    }
}
