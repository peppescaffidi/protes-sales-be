package com.giuseppescaffidi.protessalesbe.service;

import com.giuseppescaffidi.protessalesbe.model.dtos.LineChartDTO;
import com.giuseppescaffidi.protessalesbe.model.dtos.UpdateHeatmapDTO;
import com.giuseppescaffidi.protessalesbe.model.dtos.UpdateTargetSellDTO;

public interface SellService {
    LineChartDTO getSellLineCharByCountry(Integer productId, Integer countryId);
    void updateTargetSell(UpdateTargetSellDTO updateTargetSellDTO);
    void updateHeatmapByRegion(UpdateHeatmapDTO updateHeatmapDTO);
}
