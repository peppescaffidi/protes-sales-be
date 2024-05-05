package com.giuseppescaffidi.protessalesbe.model.entities.mongodb;

import java.util.Comparator;

public class CoverageComparator implements Comparator<CoverageDocument> {
    @Override
    public int compare(CoverageDocument o1, CoverageDocument o2) {
        return o1.getMonth().compareTo(o2.getMonth());
    }
}
