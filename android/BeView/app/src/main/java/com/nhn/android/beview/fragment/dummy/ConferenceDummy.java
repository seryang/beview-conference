package com.nhn.android.beview.fragment.dummy;

import com.nhn.android.beview.model.Conference;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by HyunSeungHo on 2017. 1. 11..
 */

public class ConferenceDummy {

    public static final List<Conference> ITEMS = new ArrayList<>();

    public static final Map<Integer, Conference> ITEM_MAP = new HashMap<>();

    private static final int COUNT = 25;

    static {
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(Conference item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getId(), item);
    }

    private static Conference createDummyItem(int position) {
        Conference conference = new Conference();
        conference.setId(1);
        conference.setEndDate(new Date());
        conference.setStartDate(new Date());
        conference.setLocation("코엑스");
        conference.setName("BeView 2017");
        conference.setStrId("beview_2017");
        return conference;
    }
}
