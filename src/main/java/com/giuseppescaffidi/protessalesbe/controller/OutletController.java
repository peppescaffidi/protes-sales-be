package com.giuseppescaffidi.protessalesbe.controller;

import com.giuseppescaffidi.protessalesbe.model.dtos.OutletDTO;
import com.giuseppescaffidi.protessalesbe.service.OutletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/sales/regional/Outlets")
public class OutletController {
    @Autowired
    OutletService outletService;

    @GetMapping("/{productId}/{countryId}/{regionId}")
    public List<OutletDTO> getOutletsByRegion(@PathVariable Integer productId,
                                                @PathVariable Integer countryId,
                                                @PathVariable Integer regionId) {
        System.out.println("***************** getOutletsByRegion *******************");

        return outletService.getOutletsByRegion(productId, countryId, regionId);
    }

    @PostMapping("/import")
    public void importOutletsFromExcel(
            @RequestParam int productId,
            @RequestParam int regionId,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        System.out.println("***************** importOutletsFromExcel *******************");

        outletService.importOutletsFromExcel(productId, regionId, file);
    }

    @PostMapping("/{productId}/{countryId}/{regionId}/upsert")
    public OutletDTO upsert(@PathVariable Integer productId, @PathVariable Integer countryId,
                            @PathVariable Integer regionId, @RequestBody OutletDTO outletDTO) {
        System.out.println("***************** upsert *******************");

        return outletService.upsert(productId, countryId, regionId, outletDTO);
    }
}
