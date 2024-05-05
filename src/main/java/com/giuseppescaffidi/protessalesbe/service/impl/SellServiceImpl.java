package com.giuseppescaffidi.protessalesbe.service.impl;

import com.giuseppescaffidi.protessalesbe.model.dtos.LineChartDTO;
import com.giuseppescaffidi.protessalesbe.model.dtos.UpdateHeatmapDTO;
import com.giuseppescaffidi.protessalesbe.model.dtos.UpdateTargetSellDTO;
import com.giuseppescaffidi.protessalesbe.model.entities.mongodb.*;
import com.giuseppescaffidi.protessalesbe.model.enums.InclusionProcessStatusEnum;
import com.giuseppescaffidi.protessalesbe.repository.SalesRepository;
import com.giuseppescaffidi.protessalesbe.service.SellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class SellServiceImpl implements SellService {
    @Autowired
    SalesRepository salesRepository;

    @Override
    public LineChartDTO getSellLineCharByCountry(Integer productId, Integer countryId) {
        ProductDocument productDocument = salesRepository.findByProductId(productId);
        LineChartDTO lineChartDTO = new LineChartDTO();

        CountryDocument countryDocument = productDocument.getCountryById(countryId);

        lineChartDTO = lineChartDTO.getLineChartFromSellList(countryDocument.getSell());

        System.out.println("***************** end getSellLineCharByCountry *******************");

        return lineChartDTO;
    }

    @Override
    public void updateTargetSell(UpdateTargetSellDTO updateTargetSellDTO) {
        ProductDocument productDocument = salesRepository
                .findByProductId(updateTargetSellDTO.getProductId());

        CountryDocument countryDocument = productDocument.getCountryById(updateTargetSellDTO.getCountryId());
        double outletPercentage = 0;
        if (!countryDocument.getRegions().isEmpty() && updateTargetSellDTO.getRegionId() != null) {
            double outletPatient = countryDocument.getRegionById(updateTargetSellDTO.getRegionId())
                    .getOutletById(updateTargetSellDTO.getDueDate()).getPotentialNumber();

            int countryTotalPatient = countryDocument.getTotalPatient();

            outletPercentage = (outletPatient * 100) / countryTotalPatient;
        }

        // Rimuovo il vecchio valore di sell se la nuova due date e' differente dalla
        // prima (solo se presente)
        if (updateTargetSellDTO.getOldDueDate() != null && !updateTargetSellDTO.getOldDueDate()
                .equals(updateTargetSellDTO.getDueDate())) {
            SellDocument oldSell = countryDocument
                    .getSellByMonth(updateTargetSellDTO.getOldDueDate());
            oldSell.setTarget(oldSell.getTarget() - outletPercentage);

            List<SellDocument> newSellList = countryDocument.getSell();

            newSellList.remove(countryDocument
                    .getSellByMonth(updateTargetSellDTO.getOldDueDate()));
            newSellList.add(oldSell);

            countryDocument.setSell(newSellList);
        }

        // Aggiungo il valore target di sell se la region non aveva ancora una data target
        // (old due date vuota) oppure se la nuova due date e' diversa dalla vecchia
        if (updateTargetSellDTO.getOldDueDate() == null || !updateTargetSellDTO.getOldDueDate().equals(updateTargetSellDTO.getDueDate())) {
            SellDocument sell = countryDocument.getSellByMonth(updateTargetSellDTO.getDueDate());

            List<SellDocument> newSellList = countryDocument.getSell();

            // Se il mese target e' gia' presente nella sell list allora lo aggiorno
            // altrimenti ne creo una nuova istanza e lo aggiungo alla lista
            if (sell != null) {
                sell.setTarget(sell.getTarget() + outletPercentage);

                newSellList.remove(countryDocument.getSellByMonth(updateTargetSellDTO.getDueDate()));

            } else {
                sell = new SellDocument();
                sell.setMonth(updateTargetSellDTO.getDueDate());
                sell.setTarget(outletPercentage);
            }

            newSellList.add(sell);

            countryDocument.setSell(newSellList);
        }

        salesRepository.save(productDocument);

        System.out.println("***************** end updateHeatmapByRegion *******************");
    }

    @Override
    public void updateHeatmapByRegion(UpdateHeatmapDTO updateHeatmapDTO) {
        ProductDocument productDocument = salesRepository.findByProductId(updateHeatmapDTO.getProductId());

        RegionDocument regionDocument = productDocument.getCountryById(updateHeatmapDTO.getCountryId())
                .getRegionById(updateHeatmapDTO.getRegionId());

        int regionTotalPatient = regionDocument.getTotalPatient();

        int outletPatient = regionDocument.getOutletById(updateHeatmapDTO.getOutletId())
                .getPotentialNumber();

        double outletPercentage = (double) (outletPatient * 100) / regionTotalPatient;

        // Se da uno stato non completato passo a completato aggiungo il valore di heatmap
        if (!Objects.equals(updateHeatmapDTO.getOldStepStatus(), InclusionProcessStatusEnum.COMPLETED.name()) &&
                Objects.equals(updateHeatmapDTO.getNewStepStatus(), InclusionProcessStatusEnum.COMPLETED.name())) {
            regionDocument.setHeatmapInclusionProcess(regionDocument.getHeatmapInclusionProcess() + outletPercentage);
        }
        // Se da uno stato completato passo a non completato rimuovo il valore di heatmap
        else if (Objects.equals(updateHeatmapDTO.getOldStepStatus(), InclusionProcessStatusEnum.COMPLETED.name()) &&
                !Objects.equals(updateHeatmapDTO.getNewStepStatus(), InclusionProcessStatusEnum.COMPLETED.name())) {
            regionDocument.setHeatmapInclusionProcess(regionDocument.getHeatmapInclusionProcess() - outletPercentage);
        }

        // Aggiornamento grafico sell
        updateActualSell(updateHeatmapDTO, productDocument);

        salesRepository.save(productDocument);

        System.out.println("***************** end updateHeatmapByRegion *******************");
    }

    public void updateActualSell(UpdateHeatmapDTO updateHeatmapDTO, ProductDocument productDocument) {
        CountryDocument countryDocument = productDocument.getCountryById(updateHeatmapDTO.getCountryId());
        double outletPercentage = 0;
        if (!countryDocument.getRegions().isEmpty() && updateHeatmapDTO.getRegionId() != null) {
            double outletPatient = countryDocument.getRegionById(updateHeatmapDTO.getRegionId())
                    .getOutletById(updateHeatmapDTO.getOutletId()).getPotentialNumber();

            int countryTotalPatient = countryDocument.getTotalPatient();

            outletPercentage = (outletPatient * 100) / countryTotalPatient;
        }

        RegionDocument regionDocument = countryDocument.getRegionById(updateHeatmapDTO.getRegionId());

        CoverageProcessStepDocument coverageProcessStepDocument = regionDocument.getInclusionProcess().getSteps()
                .stream().filter(CoverageProcessStepDocument::isSell).toList().get(0);

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        String format = formatter.format(date);

        // Rimuovo il vecchio valore di sell se la nuova due date e' differente dalla
        // prima (solo se presente)
        if (coverageProcessStepDocument.getEndDate() != null && !coverageProcessStepDocument.getEndDate()
                .equals(format)) {
            SellDocument oldSell = countryDocument
                    .getSellByMonth(coverageProcessStepDocument.getEndDate());
            oldSell.setReal(oldSell.getReal() - outletPercentage);

            List<SellDocument> newSellList = countryDocument.getSell();

            newSellList.remove(countryDocument
                    .getSellByMonth(coverageProcessStepDocument.getEndDate()));
            newSellList.add(oldSell);

            countryDocument.setSell(newSellList);
        }

        // Aggiungo il valore actual di sell se la region non aveva ancora una data
        // (end date vuota) oppure se la nuova date e' diversa dalla vecchia
        if (coverageProcessStepDocument.getEndDate() == null || !coverageProcessStepDocument.getEndDate().equals(format)) {
            SellDocument sell = countryDocument.getSellByMonth(format);

            List<SellDocument> newSellList = countryDocument.getSell();

            // Se il mese target e' gia' presente nella sell list allora lo aggiorno
            // altrimenti ne creo una nuova istanza e lo aggiungo alla lista
            if (sell != null) {
                sell.setReal(sell.getReal() + outletPercentage);

                newSellList.remove(countryDocument.getSellByMonth(format));

            } else {
                sell = new SellDocument();
                sell.setMonth(format);
                sell.setReal(outletPercentage);
            }

            newSellList.add(sell);

            countryDocument.setSell(newSellList);
        }

        System.out.println("***************** end updateHeatmapByRegion *******************");
    }
}
