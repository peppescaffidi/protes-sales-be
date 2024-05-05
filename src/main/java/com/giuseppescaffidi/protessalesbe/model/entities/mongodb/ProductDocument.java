package com.giuseppescaffidi.protessalesbe.model.entities.mongodb;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.NoSuchElementException;

@Data
@Document(collection = "products")
public class ProductDocument {
    @Id
    private ObjectId _id;
    private Integer productId;
    private String businessName;
    private List<CountryDocument> countries;

    public CountryDocument getCountryById(Integer countryId) {
        CountryDocument countryDocumentError = new CountryDocument();
        try {
            return countries.stream().filter(countryDocument -> countryDocument.getCountryId().equals(countryId))
                    .findFirst().orElseThrow();
        } catch (NoSuchElementException noSuchElementException) {
            System.err.println("************* getCoverageLineCharByCountry - no country found ***************");
            countryDocumentError.setName("No country found");
            return countryDocumentError;
        }
    }
}
