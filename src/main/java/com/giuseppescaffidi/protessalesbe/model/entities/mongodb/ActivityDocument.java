package com.giuseppescaffidi.protessalesbe.model.entities.mongodb;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "activities")
public class ActivityDocument {
    @Id
    ObjectId _id;
    Integer activityId;
    String name;
}
