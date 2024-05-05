package com.giuseppescaffidi.protessalesbe.service.impl;

import com.giuseppescaffidi.protessalesbe.model.dtos.ActivityDTO;
import com.giuseppescaffidi.protessalesbe.model.dtos.ActivityPlanDTO;
import com.giuseppescaffidi.protessalesbe.model.entities.mongodb.ActivityDocument;
import com.giuseppescaffidi.protessalesbe.model.entities.mongodb.ActivityPlanDocument;
import com.giuseppescaffidi.protessalesbe.model.entities.mongodb.ProductDocument;
import com.giuseppescaffidi.protessalesbe.model.entities.mongodb.RegionDocument;
import com.giuseppescaffidi.protessalesbe.repository.ActivityRepository;
import com.giuseppescaffidi.protessalesbe.repository.SalesRepository;
import com.giuseppescaffidi.protessalesbe.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    ActivityRepository activityRepository;
    @Autowired
    SalesRepository salesRepository;

    @Override
    public List<ActivityPlanDTO> getActivityPlanPerRegion(Integer productId, Integer countryId, Integer regionId) {
        ProductDocument productDocument = salesRepository.findByProductId(productId);

        List<ActivityPlanDTO> activityPlanDTOS = ActivityPlanDTO.getActivityPlanDTOListFromActivityPlanDocumentList(
                productDocument.getCountryById(countryId).getRegionById(regionId).getActivityPlan()
        );

        activityPlanDTOS.sort((o1, o2) -> (o1.getActivityId().compareTo(o2.getActivityId())));

        System.out.println("***************** end getActivityPlanPerRegion *******************");

        return activityPlanDTOS;
    }

    @Override
    public List<ActivityPlanDTO> importActivitiesFromList(Integer productId, Integer countryId, Integer regionId,
                                                          List<ActivityDTO> activityDTOS) {
        ProductDocument productDocument = salesRepository.findByProductId(productId);
        RegionDocument regionDocument = productDocument.getCountryById(countryId).getRegionById(regionId);

        List<ActivityPlanDocument> activityPlanDocuments = new ArrayList<>();

        activityDTOS.forEach(activityDTO -> {
            ActivityPlanDocument activityPlanDocument = new ActivityPlanDocument();
            activityPlanDocument.setActivityId(activityDTO.getActivityId());
            activityPlanDocument.setName(activityDTO.getName());
            activityPlanDocuments.add(activityPlanDocument);
        });

        regionDocument.addAllActivityPlan(activityPlanDocuments);

        productDocument = salesRepository.save(productDocument);

        return ActivityPlanDTO.getActivityPlanDTOListFromActivityPlanDocumentList(
                productDocument.getCountryById(countryId).getRegionById(regionId).getActivityPlan());
    }

    @Override
    public ActivityPlanDTO update(Integer productId, Integer countryId, Integer regionId,
                                  ActivityPlanDTO activityPlanDTO) {
        ProductDocument productDocument = salesRepository.findByProductId(productId);

        List<ActivityPlanDocument> activityPlanDocuments = productDocument.getCountryById(countryId)
                .getRegionById(regionId).getActivityPlan();

        ActivityPlanDocument activityPlanDocumentToEdit = activityPlanDocuments.stream()
                .filter(activity -> activity.getActivityId().equals(activityPlanDTO.getActivityId())).toList().get(0);

        ActivityPlanDocument activityPlanDocument = new ActivityPlanDocument(activityPlanDTO);

        activityPlanDocuments.remove(activityPlanDocumentToEdit);
        activityPlanDocuments.add(activityPlanDocument);

        productDocument = salesRepository.save(productDocument);

        System.out.println("document saved - " + productDocument);

        System.out.println("***************** end update *******************");

        return activityPlanDTO;
    }
}
