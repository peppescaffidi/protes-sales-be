package com.giuseppescaffidi.protessalesbe.service.impl;

import com.giuseppescaffidi.protessalesbe.model.dtos.LineChartDTO;
import com.giuseppescaffidi.protessalesbe.model.dtos.UpdateTargetCoverageDTO;
import com.giuseppescaffidi.protessalesbe.model.entities.mongodb.CountryDocument;
import com.giuseppescaffidi.protessalesbe.model.entities.mongodb.ProductDocument;
import com.giuseppescaffidi.protessalesbe.model.entities.mongodb.CoverageDocument;
import com.giuseppescaffidi.protessalesbe.repository.SalesRepository;
import com.giuseppescaffidi.protessalesbe.service.CoverageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoverageServiceImpl implements CoverageService {
    @Autowired
    SalesRepository salesRepository;

    @Override
    public LineChartDTO getCoverageLineCharByCountry(Integer productId, Integer countryId) {
        ProductDocument productDocument = salesRepository.findByProductId(productId);
        LineChartDTO lineChartDTO = new LineChartDTO();

        CountryDocument countryDocument = productDocument.getCountryById(countryId);

        lineChartDTO = lineChartDTO.getLineChartFromCoverageList(countryDocument.getCoverage());

        System.out.println("***************** end getCoverageLineCharByCountry *******************");

        return lineChartDTO;
    }

    @Override
    public void updateTargetCoverage(UpdateTargetCoverageDTO updateTargetCoverageDTO) {
        ProductDocument productDocument = salesRepository
                .findByProductId(updateTargetCoverageDTO.getProductId());

        CountryDocument countryDocument = productDocument.getCountryById(updateTargetCoverageDTO.getCountryId());
        double regionPotential = 0;
        if (!countryDocument.getRegions().isEmpty() && updateTargetCoverageDTO.getRegionId() != null) {
            regionPotential = countryDocument.getRegionById(updateTargetCoverageDTO.getRegionId()).getPotential();
        }

        // Rimuovo il vecchio valore di coverage se la nuova due date è differente dalla prima (solo se presente)
        if (updateTargetCoverageDTO.getOldDueDate() != null &&
                        !updateTargetCoverageDTO.getOldDueDate().equals(updateTargetCoverageDTO.getDueDate())) {

            CoverageDocument oldCoverage = countryDocument.getCoverageByMonth(updateTargetCoverageDTO
                    .getOldDueDate());
            oldCoverage.setTarget(oldCoverage.getTarget() - regionPotential);

            List<CoverageDocument> newCoverageList = countryDocument.getCoverage();

            newCoverageList.remove(countryDocument.getCoverageByMonth(updateTargetCoverageDTO.getOldDueDate()));
            newCoverageList.add(oldCoverage);

            countryDocument.setCoverage(newCoverageList);
        }

        // Aggiungo il valore target di coverage se la region non aveva ancora una data target
        // (old due date vuota) oppure se la nuova due date e' diversa dalla vecchia
        if (updateTargetCoverageDTO.getOldDueDate() == null ||
                !updateTargetCoverageDTO.getOldDueDate().equals(updateTargetCoverageDTO.getDueDate())) {

            CoverageDocument coverage = countryDocument.getCoverageByMonth(updateTargetCoverageDTO.getDueDate());

            List<CoverageDocument> newCoverageList = countryDocument.getCoverage();

            // Se il mese target e' gia' presente nella coverage list allora lo aggiorno
            // altrimenti ne creo una nuova istanza e lo aggiungo alla lista
            if (coverage != null) {
                coverage.setTarget(coverage.getTarget() + regionPotential);

                newCoverageList.remove(
                        countryDocument.getCoverageByMonth(updateTargetCoverageDTO.getDueDate())
                );

            } else {
                coverage = new CoverageDocument();
                coverage.setMonth(updateTargetCoverageDTO.getDueDate());
                coverage.setTarget(regionPotential);
            }

            newCoverageList.add(coverage);

            countryDocument.setCoverage(newCoverageList);
        }

        salesRepository.save(productDocument);
    }

    @Override
    public void updateActualCoverage(UpdateTargetCoverageDTO updateTargetCoverageDTO) {
        ProductDocument productDocument = salesRepository
                .findByProductId(updateTargetCoverageDTO.getProductId());

        CountryDocument countryDocument = productDocument.getCountryById(updateTargetCoverageDTO.getCountryId());
        double regionPotential = 0;
        if (!countryDocument.getRegions().isEmpty() && updateTargetCoverageDTO.getRegionId() != null) {
            regionPotential = countryDocument.getRegionById(updateTargetCoverageDTO.getRegionId()).getPotential();
        }

        // Rimuovo il vecchio valore di coverage se la nuova due date e' differente dalla
        // prima (solo se presente)
        if (updateTargetCoverageDTO.getOldDueDate() != null && !updateTargetCoverageDTO.getOldDueDate().equals(updateTargetCoverageDTO.getDueDate())) {
            CoverageDocument oldCoverage = countryDocument.getCoverageByMonth(updateTargetCoverageDTO.getOldDueDate());
            oldCoverage.setReal(oldCoverage.getReal() - regionPotential);

            List<CoverageDocument> newCoverageList = countryDocument.getCoverage();

            newCoverageList.remove(countryDocument.getCoverageByMonth(updateTargetCoverageDTO.getOldDueDate()));
            newCoverageList.add(oldCoverage);

            countryDocument.setCoverage(newCoverageList);
        }

        // Aggiungo il valore target di coverage se la region non aveva ancora una data target
        // (old due date vuota) oppure se la nuova due date e' diversa dalla vecchia
        if (updateTargetCoverageDTO.getOldDueDate() == null || !updateTargetCoverageDTO.getOldDueDate().equals(updateTargetCoverageDTO.getDueDate())) {
            CoverageDocument coverage = countryDocument.getCoverageByMonth(updateTargetCoverageDTO.getDueDate());

            List<CoverageDocument> newCoverageList = countryDocument.getCoverage();

            // Se il mese target e' gia' presente nella coverage list allora lo aggiorno
            // altrimenti ne creo una nuova istanza e lo aggiungo alla lista
            if (coverage != null) {
                coverage.setReal(coverage.getReal() + regionPotential);

                newCoverageList.remove(countryDocument.getCoverageByMonth(updateTargetCoverageDTO.getDueDate()));

            } else {
                coverage = new CoverageDocument();
                coverage.setMonth(updateTargetCoverageDTO.getDueDate());
                coverage.setReal(regionPotential);
            }

            newCoverageList.add(coverage);

            countryDocument.setCoverage(newCoverageList);
        }

        salesRepository.save(productDocument);
    }
}
