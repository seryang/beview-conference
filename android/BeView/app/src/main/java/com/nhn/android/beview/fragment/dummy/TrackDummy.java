package com.nhn.android.beview.fragment.dummy;

import com.nhn.android.beview.model.Section;
import com.nhn.android.beview.model.Track;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by HyunSeungHo on 2017. 1. 11..
 */

public class TrackDummy {

    public static final List<Track> ITEMS = new ArrayList<>();

    public static final Map<Integer, Track> ITEM_MAP = new HashMap<>();

    private static final int COUNT = 25;

    static {
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(Track item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getIdx(), item);
    }

    private static Track createDummyItem(int position) {
        Track track = new Track();
        track.setIdx(1);
        track.setName("Track " + position);
        track.setLocation("101호");
        track.setConferenceIdx(1);
        return track;
    }
}
