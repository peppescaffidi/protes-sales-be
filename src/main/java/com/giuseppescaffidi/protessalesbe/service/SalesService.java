package com.giuseppescaffidi.protessalesbe.service;

import com.giuseppescaffidi.protessalesbe.model.dtos.InclusionProcessDTO;
import com.giuseppescaffidi.protessalesbe.model.dtos.CoverageProcessStepDTO;
import com.giuseppescaffidi.protessalesbe.model.dtos.RegionPercentageDTO;

import java.util.List;

public interface SalesService {
    List<RegionPercentageDTO> getPotentialPerRegion(Integer product, Integer country);

    List<RegionPercentageDTO> getHeatmapPerRegion(Integer product, Integer country);

    InclusionProcessDTO getInclusionProcessStepsByRegion(Integer productId, Integer countryId,
                                                         Integer regionId, String outletOnekey);

    InclusionProcessDTO updateInclusionStepProcessByRegionAndOutlet(Integer productId, Integer countryId,
                                                                      Integer regionId, String outletOnekey,
                                                                      CoverageProcessStepDTO inclusionProcessStepDTO);
}
