package com.giuseppescaffidi.protessalesbe.model.entities.mongodb;

import lombok.Data;

import java.util.List;
import java.util.NoSuchElementException;

@Data
public class CountryDocument {
    private Integer countryId;
    private String name;
    private List<CoverageDocument> coverage;
    private List<SellDocument> sell;
    private List<RegionDocument> regions;

    public int getTotalPatient() {
        return this.regions.stream().map(RegionDocument::getTotalPatient).reduce(0, Integer::sum);
    }

    public CoverageDocument getCoverageByMonth(String month) {
        List<CoverageDocument> coverageDocuments = this.getCoverage().stream()
                .filter(coverageDocument -> coverageDocument.getMonth().equals(month)).toList();

        if (!coverageDocuments.isEmpty()) {
            return coverageDocuments.get(0);
        }
        return null;
    }

    public SellDocument getSellByMonth(String month) {
        List<SellDocument> sellDocuments = this.getSell().stream()
                .filter(sellDocument -> sellDocument.getMonth().equals(month)).toList();

        if (!sellDocuments.isEmpty()) {
            return sellDocuments.get(0);
        }
        return null;
    }

    public RegionDocument getRegionById(Integer regionId) {
        RegionDocument regionDocumentError = new RegionDocument();
        try {
            return regions.stream().filter(regionDocument -> regionDocument.getRegionId() == regionId)
                    .findFirst().orElseThrow();
        } catch (NoSuchElementException noSuchElementException) {
            System.err.println("************* getRegionById - no region found ***************");
            regionDocumentError.setName("No region found");
            return regionDocumentError;
        }
    }

    public void setRegionsPotential() {
        int countryTotalPatient = getTotalPatient();

        this.regions.forEach(regionDocument -> {
            regionDocument.setPotential((double) (regionDocument.getTotalPatient() * 100) / countryTotalPatient);
        });
    }
}
