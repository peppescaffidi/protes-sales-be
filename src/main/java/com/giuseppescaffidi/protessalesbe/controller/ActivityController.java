package com.giuseppescaffidi.protessalesbe.controller;

import com.giuseppescaffidi.protessalesbe.model.dtos.ActivityDTO;
import com.giuseppescaffidi.protessalesbe.model.dtos.ActivityPlanDTO;
import com.giuseppescaffidi.protessalesbe.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/activity")
public class ActivityController {
    @Autowired
    ActivityService activityService;

    @GetMapping("/{productId}/{countryId}/{regionId}")
    public List<ActivityPlanDTO> getActivityPlanPerRegion(@PathVariable Integer productId,
                                                          @PathVariable Integer countryId,
                                                          @PathVariable Integer regionId) {
        System.out.println("***************** start getActivityPlanPerRegion *******************");

        return activityService.getActivityPlanPerRegion(productId, countryId, regionId);
    }

    @PostMapping("/{productId}/{countryId}/{regionId}/import")
    public List<ActivityPlanDTO> importActivitiesFromList(
            @PathVariable Integer productId,
            @PathVariable Integer countryId,
            @PathVariable Integer regionId,
            @RequestBody List<ActivityDTO> activityDTOS) {
        return activityService.importActivitiesFromList(productId, countryId, regionId, activityDTOS);
    }

    @PostMapping("/{productId}/{countryId}/{regionId}/update")
    public ActivityPlanDTO update(@PathVariable Integer productId,
                                  @PathVariable Integer countryId,
                                  @PathVariable Integer regionId,
                                  @RequestBody ActivityPlanDTO activityPlanDTO) {
        System.out.println("***************** start update *******************");

        return activityService.update(productId, countryId, regionId, activityPlanDTO);
    }
}
