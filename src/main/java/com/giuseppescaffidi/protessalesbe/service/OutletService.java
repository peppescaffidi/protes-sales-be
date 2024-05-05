package com.giuseppescaffidi.protessalesbe.service;

import com.giuseppescaffidi.protessalesbe.model.dtos.OutletDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface OutletService {
    List<OutletDTO> getOutletsByRegion(Integer productId, Integer countryId, Integer regionId);

    void importOutletsFromExcel(int productId, int regionId, MultipartFile file) throws IOException;

    OutletDTO upsert(Integer productId, Integer countryId, Integer regionId, OutletDTO outletDTO);
}
