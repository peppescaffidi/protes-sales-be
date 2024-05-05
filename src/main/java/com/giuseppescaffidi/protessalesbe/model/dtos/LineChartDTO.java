package com.giuseppescaffidi.protessalesbe.model.dtos;

import com.giuseppescaffidi.protessalesbe.model.entities.mongodb.CoverageComparator;
import com.giuseppescaffidi.protessalesbe.model.entities.mongodb.CoverageDocument;
import com.giuseppescaffidi.protessalesbe.model.entities.mongodb.SellComparator;
import com.giuseppescaffidi.protessalesbe.model.entities.mongodb.SellDocument;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Data
public class LineChartDTO {
    private List<String> labels;
    private List<Double> target;
    private List<Double> real;

    public LineChartDTO getLineChartFromCoverageList(List<CoverageDocument> coverageDocuments) {
        LineChartDTO lineChartDTO = new LineChartDTO();

        if (coverageDocuments != null) {
            coverageDocuments.sort(new CoverageComparator());

            lineChartDTO.setLabels(coverageDocuments.stream().map(CoverageDocument::getMonth).toList());

            List<Double> target = new ArrayList<>();
            AtomicInteger targetCounter = new AtomicInteger();

            List<Double> real = new ArrayList<>();
            AtomicInteger realCounter = new AtomicInteger();

            coverageDocuments.forEach(coverageDocument -> {
                targetCounter.addAndGet((int) coverageDocument.getTarget());
                target.add(targetCounter.doubleValue());

                realCounter.addAndGet((int) coverageDocument.getReal());
                real.add(realCounter.doubleValue());
            });

            lineChartDTO.setTarget(target);
            lineChartDTO.setReal(real);
        } else {
            lineChartDTO.setLabels(List.of(new String[]{"Empty list"}));
        }

        return lineChartDTO;
    }

    public LineChartDTO getLineChartFromSellList(List<SellDocument> sellDocuments) {
        LineChartDTO lineChartDTO = new LineChartDTO();

        if (sellDocuments != null) {
            sellDocuments.sort(new SellComparator());

            lineChartDTO.setLabels(sellDocuments.stream().map(SellDocument::getMonth).toList());

            List<Double> target = new ArrayList<>();
            AtomicInteger targetCounter = new AtomicInteger();

            List<Double> real = new ArrayList<>();
            AtomicInteger realCounter = new AtomicInteger();

            sellDocuments.forEach(sellDocument -> {
                targetCounter.addAndGet((int) sellDocument.getTarget());
                target.add(targetCounter.doubleValue());

                realCounter.addAndGet((int) sellDocument.getReal());
                real.add(realCounter.doubleValue());
            });

            lineChartDTO.setTarget(target);
            lineChartDTO.setReal(real);
        } else {
            lineChartDTO.setLabels(List.of(new String[]{"Empty list"}));
        }

        return lineChartDTO;
    }
}
