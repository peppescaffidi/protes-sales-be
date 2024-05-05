package com.giuseppescaffidi.protessalesbe.controller;

import com.giuseppescaffidi.protessalesbe.model.dtos.LineChartDTO;
import com.giuseppescaffidi.protessalesbe.model.dtos.UpdateHeatmapDTO;
import com.giuseppescaffidi.protessalesbe.model.dtos.UpdateTargetSellDTO;
import com.giuseppescaffidi.protessalesbe.service.SellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/sales/sell")
public class SellController {

    @Autowired
    SellService sellService;

    @GetMapping("/line-chart/{productId}/{countryId}")
    public LineChartDTO getSellLineCharByCountry(@PathVariable Integer productId, @PathVariable Integer countryId) {
        System.out.println("***************** start getSellLineCharByCountry *******************");
        return sellService.getSellLineCharByCountry(productId, countryId);
    }

    @PostMapping("/target/update")
    public void updateTargetSell(@RequestBody UpdateTargetSellDTO updateTargetSellDTO) {
        System.out.println("***************** start updateHeatmapByRegion *******************");
        sellService.updateTargetSell(updateTargetSellDTO);
    }

    @PostMapping("/heatmap/update")
    public void updateHeatmapByRegion(@RequestBody UpdateHeatmapDTO updateHeatmapDTO) {
        System.out.println("***************** start updateHeatmapByRegion *******************");
        sellService.updateHeatmapByRegion(updateHeatmapDTO);
    }
}
