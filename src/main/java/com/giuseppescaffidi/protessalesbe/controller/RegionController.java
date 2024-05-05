package com.giuseppescaffidi.protessalesbe.controller;

import com.giuseppescaffidi.protessalesbe.model.dtos.RegionGeneralInfoDTO;
import com.giuseppescaffidi.protessalesbe.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sales/regional")
public class RegionController {
    @Autowired
    RegionService regionService;

    @GetMapping("/{productId}/{countryId}/{regionId}")
    public RegionGeneralInfoDTO getGeneralInfoByRegion(@PathVariable Integer productId,
                                                       @PathVariable Integer countryId,
                                                       @PathVariable Integer regionId) {
        System.out.println("***************** getGeneralInfoByRegion *******************");

        return regionService.getGeneralInfoByRegion(productId, countryId, regionId);
    }

    @PostMapping("/{productId}/{countryId}/{regionId}/update")
    public RegionGeneralInfoDTO updateRegion(@PathVariable Integer productId, @PathVariable Integer countryId,
                                             @PathVariable Integer regionId,
                                             @RequestBody RegionGeneralInfoDTO regionGeneralInfoDTO) {
        System.out.println("***************** updateRegion *******************");

        return regionService.updateRegion(productId, countryId, regionId, regionGeneralInfoDTO);
    }
}
