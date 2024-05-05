package com.giuseppescaffidi.protessalesbe.repository;

import com.giuseppescaffidi.protessalesbe.model.entities.mongodb.ActivityDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
public interface ActivityRepository extends MongoRepository<ActivityDocument, String> {
}
