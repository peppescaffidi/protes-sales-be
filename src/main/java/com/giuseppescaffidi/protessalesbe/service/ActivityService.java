package com.giuseppescaffidi.protessalesbe.service;

import com.giuseppescaffidi.protessalesbe.model.dtos.ActivityDTO;
import com.giuseppescaffidi.protessalesbe.model.dtos.ActivityPlanDTO;

import java.util.List;

public interface ActivityService {

    List<ActivityPlanDTO> getActivityPlanPerRegion(Integer productId, Integer countryId, Integer regionId);

    List<ActivityPlanDTO> importActivitiesFromList(Integer productId, Integer countryId, Integer regionId,
                                                   List<ActivityDTO> activityDTOS);

    ActivityPlanDTO update(Integer productId, Integer countryId, Integer regionId, ActivityPlanDTO activityPlanDTO);
}
