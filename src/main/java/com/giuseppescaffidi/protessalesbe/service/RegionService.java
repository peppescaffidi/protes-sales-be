package com.giuseppescaffidi.protessalesbe.service;

import com.giuseppescaffidi.protessalesbe.model.dtos.RegionGeneralInfoDTO;

public interface RegionService {
    RegionGeneralInfoDTO getGeneralInfoByRegion(Integer productId, Integer countryId, Integer regionId);
    RegionGeneralInfoDTO updateRegion(Integer productId, Integer countryId, Integer regionId,
                                      RegionGeneralInfoDTO regionGeneralInfoDTO);
}
