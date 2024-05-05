package com.giuseppescaffidi.protessalesbe.model.dtos;

import lombok.Data;

@Data
public class HeatmapPerRegionDTO {
    private String regionName;
    private int percentage;
}
