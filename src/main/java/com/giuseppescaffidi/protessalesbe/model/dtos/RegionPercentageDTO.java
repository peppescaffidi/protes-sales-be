package com.giuseppescaffidi.protessalesbe.model.dtos;

import com.giuseppescaffidi.protessalesbe.model.entities.mongodb.RegionDocument;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RegionPercentageDTO {
    private int regionId;
    private String regionName;
    private double percentage;

    public List<RegionPercentageDTO> getPotentialPerRegionDTOListByRegions(List<RegionDocument> regionDocuments) {
        List<RegionPercentageDTO> regionPercentageDTOS = new ArrayList<>();
        if (regionDocuments != null) {
            regionDocuments.forEach(regionDocument -> {
                RegionPercentageDTO regionPercentageDTO = new RegionPercentageDTO();
                regionPercentageDTO.setRegionId(regionDocument.getRegionId());
                regionPercentageDTO.setRegionName(regionDocument.getName());
                regionPercentageDTO.setPercentage(regionDocument.getPotential());

                regionPercentageDTOS.add(regionPercentageDTO);
            });
        } else {
            RegionPercentageDTO regionPercentageDTO = new RegionPercentageDTO();
            regionPercentageDTO.setRegionName("No regions found");
            regionPercentageDTOS.add(regionPercentageDTO);
        }

        return regionPercentageDTOS;
    }

    public List<RegionPercentageDTO> getHeatmapPerRegionDTOListByRegions(List<RegionDocument> regionDocuments) {
        List<RegionPercentageDTO> regionPercentageDTOS = new ArrayList<>();
        if (regionDocuments != null) {
            regionDocuments.forEach(regionDocument -> {
                RegionPercentageDTO regionPercentageDTO = new RegionPercentageDTO();
                regionPercentageDTO.setRegionId(regionDocument.getRegionId());
                regionPercentageDTO.setRegionName(regionDocument.getName());
                regionPercentageDTO.setPercentage(regionDocument.getHeatmapInclusionProcess());

                regionPercentageDTOS.add(regionPercentageDTO);
            });
        } else {
            RegionPercentageDTO regionPercentageDTO = new RegionPercentageDTO();
            regionPercentageDTO.setRegionName("No regions found");
            regionPercentageDTOS.add(regionPercentageDTO);
        }

        return regionPercentageDTOS;
    }
}
