package com.nhn.android.beview.fragment.dummy;

import com.nhn.android.beview.model.Section;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by HyunSeungHo on 2017. 1. 11..
 */

public class SectionDummy {

    public static final List<Section> ITEMS = new ArrayList<>();

    public static final Map<Integer, Section> ITEM_MAP = new HashMap<>();

    private static final int COUNT = 25;

    static {
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(Section item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getIdx(), item);
    }

    private static Section createDummyItem(int position) {
        Section section = new Section();
        section.setIdx(1);
        section.setName("안드로이드 강의 " + position);
        section.setDescription("이 세션을 통해 많은 내용을 얻을 수 있습니다.");
        section.setEndTime(new Date());
        section.setStartTime(new Date());
        section.setFile("beview2017.pdf");
        section.setSpeakerIdx(1);
        section.setTrackName("Track 1");
        section.setTrackIdx(1);
        return section;
    }
}
