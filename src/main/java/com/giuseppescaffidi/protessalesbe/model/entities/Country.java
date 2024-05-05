package com.giuseppescaffidi.protessalesbe.model.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Country {
    @Id
    private Integer id;
    private String name;
}
