package com.giuseppescaffidi.protessalesbe.controller;

import com.giuseppescaffidi.protessalesbe.model.dtos.LineChartDTO;
import com.giuseppescaffidi.protessalesbe.model.dtos.UpdateTargetCoverageDTO;
import com.giuseppescaffidi.protessalesbe.service.CoverageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/sales/coverage")
public class CoverageController {
    @Autowired
    CoverageService coverageService;

    @GetMapping("/line-chart/{productId}/{countryId}")
    public LineChartDTO getCoverageLineCharByCountry(@PathVariable Integer productId, @PathVariable Integer countryId) {
        System.out.println("***************** start getCoverageLineCharByCountry *******************");
        return coverageService.getCoverageLineCharByCountry(productId, countryId);
    }

    @PostMapping("/target/update")
    public void updateTargetCoverage(@RequestBody UpdateTargetCoverageDTO updateTargetCoverageDTO) {
        coverageService.updateTargetCoverage(updateTargetCoverageDTO);
    }

    @PostMapping("/actual/update")
    public void updateActualCoverage(@RequestBody UpdateTargetCoverageDTO updateTargetCoverageDTO) {
        coverageService.updateActualCoverage(updateTargetCoverageDTO);
    }
}
