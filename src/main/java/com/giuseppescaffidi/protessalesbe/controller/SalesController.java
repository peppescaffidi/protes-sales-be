package com.giuseppescaffidi.protessalesbe.controller;

import com.giuseppescaffidi.protessalesbe.model.dtos.InclusionProcessDTO;
import com.giuseppescaffidi.protessalesbe.model.dtos.CoverageProcessStepDTO;
import com.giuseppescaffidi.protessalesbe.model.dtos.RegionPercentageDTO;
import com.giuseppescaffidi.protessalesbe.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/sales")
public class SalesController {
    @Autowired
    SalesService salesService;

    @GetMapping("/potential/{productId}/{countryId}")
    public List<RegionPercentageDTO> getPotentialPerRegion(@PathVariable Integer productId,
                                                           @PathVariable Integer countryId) {
        System.out.println("***************** getPotentialPerRegion *******************");

        return salesService.getPotentialPerRegion(productId, countryId);
    }

    @GetMapping("/heatmap/{productId}/{countryId}")
    public List<RegionPercentageDTO> getHeatmapPerRegion(@PathVariable Integer productId,
                                                         @PathVariable Integer countryId) {
        System.out.println("***************** getPotentialPerRegion *******************");

        return salesService.getHeatmapPerRegion(productId, countryId);
    }

    @GetMapping("/regional/inclusion-process/{productId}/{countryId}/{regionId}/{outlet}/steps")
    public InclusionProcessDTO getInclusionProcessStepsByRegion(
            @PathVariable Integer productId, @PathVariable Integer countryId, @PathVariable Integer regionId,
            @PathVariable("outlet") String outletOnekey
    ) {
        System.out.println("***************** getInclusionProcessStepsByRegion *******************");

        return salesService.getInclusionProcessStepsByRegion(productId, countryId, regionId, outletOnekey);
    }

    @PostMapping("/regional/inclusion-process/{productId}/{countryId}/{regionId}/{outlet}/update")
    public InclusionProcessDTO updateInclusionStepProcessByRegionAndOutlet(
            @PathVariable Integer productId,
            @PathVariable Integer countryId,
            @PathVariable Integer regionId,
            @PathVariable("outlet") String outletOnekey,
            @RequestBody CoverageProcessStepDTO inclusionProcessStepDTO
    ) {
        System.out.println("***************** updateInclusionStepProcessByRegionAndOutlet *******************");

        return salesService.updateInclusionStepProcessByRegionAndOutlet(productId, countryId, regionId,
                outletOnekey, inclusionProcessStepDTO);
    }
}
