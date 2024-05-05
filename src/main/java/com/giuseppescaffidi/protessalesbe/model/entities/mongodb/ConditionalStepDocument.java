package com.giuseppescaffidi.protessalesbe.model.entities.mongodb;

import lombok.Data;

@Data
public class ConditionalStepDocument {
    private int success;
    private int failed;
}
