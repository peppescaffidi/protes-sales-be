package com.giuseppescaffidi.protessalesbe.model.entities.mongodb;

import java.util.Comparator;

public class SellComparator implements Comparator<SellDocument> {
    @Override
    public int compare(SellDocument o1, SellDocument o2) {
        return o1.getMonth().compareTo(o2.getMonth());
    }
}
