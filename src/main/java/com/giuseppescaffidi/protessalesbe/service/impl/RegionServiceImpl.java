package com.giuseppescaffidi.protessalesbe.service.impl;

import com.giuseppescaffidi.protessalesbe.model.dtos.RegionGeneralInfoDTO;
import com.giuseppescaffidi.protessalesbe.model.entities.mongodb.ProductDocument;
import com.giuseppescaffidi.protessalesbe.repository.SalesRepository;
import com.giuseppescaffidi.protessalesbe.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegionServiceImpl implements RegionService {
    @Autowired
    SalesRepository salesRepository;

    @Override
    public RegionGeneralInfoDTO getGeneralInfoByRegion(Integer productId, Integer countryId, Integer regionId) {
        ProductDocument productDocument = salesRepository.findByProductId(productId);

        RegionGeneralInfoDTO regionGeneralInfoDTO = new RegionGeneralInfoDTO(productDocument.getCountryById(countryId)
                .getRegionById(regionId).getGeneralInfo());

        System.out.println("***************** end getGeneralInfoByRegion *******************");

        return regionGeneralInfoDTO;
    }

    @Override
    public RegionGeneralInfoDTO updateRegion(Integer productId, Integer countryId, Integer regionId,
                                             RegionGeneralInfoDTO regionGeneralInfoDTO) {
        ProductDocument productDocument = salesRepository.findByProductId(productId);

        productDocument.getCountryById(countryId).getRegionById(regionId).setGeneralInfo(regionGeneralInfoDTO);

        salesRepository.save(productDocument);

        System.out.println("***************** end - updateRegion *******************");

        return regionGeneralInfoDTO;
    }
}
