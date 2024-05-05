package com.giuseppescaffidi.protessalesbe.model.entities.mongodb;

import lombok.Data;

@Data
public class SellDocument {
    private String month;
    private double target;
    private double real;
}
