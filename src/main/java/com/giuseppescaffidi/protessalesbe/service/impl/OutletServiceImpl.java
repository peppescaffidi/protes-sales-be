package com.giuseppescaffidi.protessalesbe.service.impl;

import com.giuseppescaffidi.protessalesbe.model.dtos.OutletDTO;
import com.giuseppescaffidi.protessalesbe.model.entities.mongodb.OutletDocument;
import com.giuseppescaffidi.protessalesbe.model.entities.mongodb.OutletCoverageProcessStepDocument;
import com.giuseppescaffidi.protessalesbe.model.entities.mongodb.CoverageProcessStepDocument;
import com.giuseppescaffidi.protessalesbe.model.entities.mongodb.ProductDocument;
import com.giuseppescaffidi.protessalesbe.model.enums.InclusionProcessStatusEnum;
import com.giuseppescaffidi.protessalesbe.repository.SalesRepository;
import com.giuseppescaffidi.protessalesbe.service.OutletService;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class OutletServiceImpl implements OutletService {
    @Autowired
    SalesRepository salesRepository;

    @Override
    public List<OutletDTO> getOutletsByRegion(Integer productId, Integer countryId, Integer regionId) {
        ProductDocument productDocument = salesRepository.findByProductId(productId);

        List<OutletDTO> outletDTOS = new ArrayList<>(productDocument.getCountryById(countryId).getRegionById(regionId)
                .getOutlets().stream().map(OutletDTO::new).toList());

        outletDTOS.sort((o1, o2) -> (o1.getId().compareTo(o2.getId())));


        System.out.println("***************** end getOutletsByRegion *******************");

        return outletDTOS;
    }

    @Override
    public void importOutletsFromExcel(int productId, int regionId, MultipartFile file) throws IOException {
        List<OutletDocument> outlets = new ArrayList<>();

        try {
            // Creo un workbook a partire dal file ricevuto in input
            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
            XSSFSheet worksheet = workbook.getSheetAt(0);

            // Ricavo una lista di outlets a partire dal worksheet
            for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
                OutletDocument outletDocument = new OutletDocument();

                XSSFRow row = worksheet.getRow(i);

                outletDocument.setId(row.getCell(0).getStringCellValue());
                outletDocument.setCluster(row.getCell(1).getStringCellValue());
                outletDocument.setName(row.getCell(2).getStringCellValue());
                outletDocument.setCity(row.getCell(3).getStringCellValue());
                outletDocument.setProvince(row.getCell(4).getStringCellValue());
                outletDocument.setPotentialNumber((int) row.getCell(5).getNumericCellValue());
                outletDocument.setPotentialPercentage(row.getCell(6).getNumericCellValue());

                outlets.add(outletDocument);
            }

            // Ricavo dal DB il prodotto per il quale sto censendo gli outlet
            ProductDocument productDocument = salesRepository.findByProductId(productId);

            if (productDocument != null && !productDocument.getCountries().isEmpty()) {
                productDocument.getCountries().forEach(countryDocument -> {
                    if (!countryDocument.getRegions().isEmpty()) {
                        countryDocument.getRegions().forEach(regionDocument -> {
                            // Ricavo la regione per la quale sto censendo gli outlet
                            if (regionDocument.getRegionId() == regionId) {

                                List<String> outletsOnekey = regionDocument.getOutlets().stream()
                                        .map(OutletDocument::getId).toList();

                                outlets.forEach(outletDocument -> {
                                    if (!outletsOnekey.contains(outletDocument.getId())) {
                                        regionDocument.getOutlets().add(outletDocument);
                                    }
                                });

                                // Aggiungo gli outlet negli step locali che mi serviranno nel processo di inclusione
                                List<CoverageProcessStepDocument> localSteps = regionDocument.getInclusionProcess()
                                        .getSteps().stream().filter(CoverageProcessStepDocument::isLocal).toList();

                                localSteps.forEach(step -> {
                                    outlets.forEach(outletDocument -> {
                                        OutletCoverageProcessStepDocument outletCoverageProcessStepDocument =
                                                new OutletCoverageProcessStepDocument();
                                        outletCoverageProcessStepDocument.setId(outletDocument.getId());
                                        outletCoverageProcessStepDocument
                                                .setStatus(InclusionProcessStatusEnum.OPEN.name());
                                        step.getOutlets().add(outletCoverageProcessStepDocument);
                                    });
                                });
                            }
                        });

                        countryDocument.setRegionsPotential();
                    }
                });

                salesRepository.save(productDocument);
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        System.out.println("***************** end importOutletsFromExcel *******************");
    }

    @Override
    public OutletDTO upsert(Integer productId, Integer countryId, Integer regionId, OutletDTO outletDTO) {
        ProductDocument productDocument = salesRepository.findByProductId(productId);

        productDocument.getCountryById(countryId).getRegionById(regionId).upsertOutlet(outletDTO);

        productDocument.getCountryById(countryId).setRegionsPotential();

        salesRepository.save(productDocument);

        System.out.println("***************** end upsert *******************");

        return outletDTO;
    }
}
