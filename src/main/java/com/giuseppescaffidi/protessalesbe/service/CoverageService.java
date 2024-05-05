package com.giuseppescaffidi.protessalesbe.service;

import com.giuseppescaffidi.protessalesbe.model.dtos.LineChartDTO;
import com.giuseppescaffidi.protessalesbe.model.dtos.UpdateTargetCoverageDTO;

public interface CoverageService {
    LineChartDTO getCoverageLineCharByCountry(Integer productId, Integer countryId);

    void updateTargetCoverage(UpdateTargetCoverageDTO updateTargetCoverageDTO);

    void updateActualCoverage(UpdateTargetCoverageDTO updateTargetCoverageDTO);
}
