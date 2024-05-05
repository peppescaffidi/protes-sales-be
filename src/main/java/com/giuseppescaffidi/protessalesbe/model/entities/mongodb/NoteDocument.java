package com.giuseppescaffidi.protessalesbe.model.entities.mongodb;

import lombok.Data;

@Data
public class NoteDocument {
    private String title;
    private String description;
    private String date;
}
