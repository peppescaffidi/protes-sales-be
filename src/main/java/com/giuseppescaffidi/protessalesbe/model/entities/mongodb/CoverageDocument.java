package com.giuseppescaffidi.protessalesbe.model.entities.mongodb;

import lombok.Data;

@Data
public class CoverageDocument {
    private String month;
    private double target;
    private double real;
}

