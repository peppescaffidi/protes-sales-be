package com.giuseppescaffidi.protessalesbe.repository;

import com.giuseppescaffidi.protessalesbe.model.entities.mongodb.ProductDocument;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface SalesRepository extends MongoRepository<ProductDocument, Integer> {
    ProductDocument findByProductId(Integer productId);
}
