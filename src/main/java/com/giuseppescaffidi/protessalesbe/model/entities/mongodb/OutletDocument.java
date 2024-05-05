package com.giuseppescaffidi.protessalesbe.model.entities.mongodb;

import com.giuseppescaffidi.protessalesbe.model.dtos.OutletDTO;
import lombok.Data;

@Data
public class OutletDocument {
    private String id;
    private String cluster;
    private String name;
    private String city;
    private String province;
    private Double averageTimeForCommissionMeeting;
    private int potentialNumber;
    private Double potentialPercentage;

    public OutletDocument() {};
    OutletDocument(OutletDTO outletDTO) {
        this.id = outletDTO.getId();
        this.cluster = outletDTO.getCluster();
        this.name = outletDTO.getName();
        this.city = outletDTO.getCity();
        this.province = outletDTO.getProvince();
        this.averageTimeForCommissionMeeting = outletDTO.getAverageTimeForCommissionMeeting();
        this.potentialNumber = outletDTO.getPotentialNumber();
        this.potentialPercentage = outletDTO.getPotentialPercentage();
    }
}
