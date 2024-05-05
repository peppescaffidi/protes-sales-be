package com.giuseppescaffidi.protessalesbe.service.impl;

import com.giuseppescaffidi.protessalesbe.model.dtos.InclusionProcessDTO;
import com.giuseppescaffidi.protessalesbe.model.dtos.CoverageProcessStepDTO;
import com.giuseppescaffidi.protessalesbe.model.dtos.RegionPercentageDTO;
import com.giuseppescaffidi.protessalesbe.repository.SalesRepository;
import com.giuseppescaffidi.protessalesbe.service.SalesService;
import com.giuseppescaffidi.protessalesbe.model.entities.mongodb.InclusionProcessDocument;
import com.giuseppescaffidi.protessalesbe.model.entities.mongodb.CoverageProcessStepDocument;
import com.giuseppescaffidi.protessalesbe.model.entities.mongodb.ProductDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SalesServiceImpl implements SalesService {
    @Autowired
    SalesRepository salesRepository;

    @Override
    public List<RegionPercentageDTO> getPotentialPerRegion(Integer productId, Integer countryId) {
        ProductDocument product = salesRepository.findByProductId(productId);

        RegionPercentageDTO regionPercentageDTO = new RegionPercentageDTO();

        List<RegionPercentageDTO> regionPercentageDTOS = regionPercentageDTO.
                getPotentialPerRegionDTOListByRegions(product.getCountryById(countryId).getRegions());

        regionPercentageDTOS.sort((o1, o2) -> (o1.getRegionName().compareTo(o2.getRegionName())));

        System.out.println("***************** end getPotentialPerRegion *******************");

        return regionPercentageDTOS;
    }

    @Override
    public List<RegionPercentageDTO> getHeatmapPerRegion(Integer productId, Integer countryId) {
        ProductDocument product = salesRepository.findByProductId(productId);

        RegionPercentageDTO regionPercentageDTO = new RegionPercentageDTO();

        List<RegionPercentageDTO> regionPercentageDTOS = regionPercentageDTO.
                getHeatmapPerRegionDTOListByRegions(product.getCountryById(countryId).getRegions());

        regionPercentageDTOS.sort((o1, o2) -> (o1.getRegionName().compareTo(o2.getRegionName())));

        System.out.println("***************** end getPotentialPerRegion *******************");

        return regionPercentageDTOS;
    }

    @Override
    public InclusionProcessDTO getInclusionProcessStepsByRegion(Integer productId, Integer countryId,
                                                                Integer regionId, String outletOnekey) {

        ProductDocument productDocument = salesRepository.findByProductId(productId);

        InclusionProcessDocument inclusionProcessDocument = productDocument.getCountryById(countryId)
                .getRegionById(regionId).getInclusionProcess();

        List<CoverageProcessStepDocument> coverageProcessStepDocuments = inclusionProcessDocument.getSteps();;

        InclusionProcessDTO inclusionProcessDTO = new InclusionProcessDTO();

        List<CoverageProcessStepDTO> inclusionProcessStepDTOS = new ArrayList<>();

        coverageProcessStepDocuments.forEach(inclusionProcessStepDocument -> {
            CoverageProcessStepDTO inclusionProcessStepDTO = new CoverageProcessStepDTO(inclusionProcessStepDocument);

            // TODO: Ottimizzare ciclo di recupero ospedali
            if (inclusionProcessStepDocument.getOutlets() != null &&
                    !inclusionProcessStepDocument.getOutlets().isEmpty()) {
                inclusionProcessStepDocument.getOutlets().forEach(outlet -> {
                    if (outlet.getId().equals(outletOnekey)) {
                        inclusionProcessStepDTO.setDueDate(outlet.getDueDate());
                        inclusionProcessStepDTO.setStatus(outlet.getStatus());
                    }
                });
            } else {
                inclusionProcessStepDTO.setDueDate(inclusionProcessStepDocument.getDueDate());
                inclusionProcessStepDTO.setStatus(inclusionProcessStepDocument.getStatus());
            }

            inclusionProcessStepDTO.setLastStep(inclusionProcessStepDocument.isLastStep());

            inclusionProcessStepDTOS.add(inclusionProcessStepDTO);
        });

        inclusionProcessDTO.setImage(inclusionProcessDocument.getImage());
        inclusionProcessDTO.setSteps(inclusionProcessStepDTOS);

        System.out.println("***************** end getInclusionProcessStepsByRegion *******************");

        return inclusionProcessDTO;
    }

    @Override
    public InclusionProcessDTO updateInclusionStepProcessByRegionAndOutlet(
            Integer productId, Integer countryId, Integer regionId, String outletOnekey,
            CoverageProcessStepDTO inclusionProcessStepDTO) {
        ProductDocument productDocument = salesRepository.findByProductId(productId);

        if (inclusionProcessStepDTO.getStepNumber() != 0) {
            InclusionProcessDocument inclusionProcessDocument = productDocument.getCountryById(countryId)
                    .getRegionById(regionId).getInclusionProcess();

            if (inclusionProcessStepDTO.isLocal()) {
                inclusionProcessStepDTO.updateLocalOutletStep(outletOnekey, inclusionProcessStepDTO);
            }

            inclusionProcessDocument.updateStep(inclusionProcessStepDTO);
        } else {
            productDocument.getCountryById(countryId).getRegions().forEach(regionDocument -> {
                InclusionProcessDocument inclusionProcessDocument = productDocument.getCountryById(countryId)
                        .getRegionById(regionDocument.getRegionId()).getInclusionProcess();

                inclusionProcessDocument.updateStep(inclusionProcessStepDTO);
            });
        }

        // Save document
        salesRepository.save(productDocument);

        System.out.println("***************** end getInclusionProcessStepsByRegion *******************");

        return new InclusionProcessDTO(productDocument.getCountryById(countryId)
                .getRegionById(regionId).getInclusionProcess());
    }
}
