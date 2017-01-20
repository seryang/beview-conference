package com.nhn.android.beview.fragment.dummy;

import com.nhn.android.beview.model.Section;
import com.nhn.android.beview.model.Speaker;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by HyunSeungHo on 2017. 1. 11..
 */

public class SpeakerDummy {

    public static final List<Speaker> ITEMS = new ArrayList<>();

    public static final Map<Integer, Speaker> ITEM_MAP = new HashMap<>();

    private static final int COUNT = 25;

    static {
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(Speaker item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getIdx(), item);
    }

    private static Speaker createDummyItem(int position) {
        Speaker speaker = new Speaker();
        speaker.setIdx(1);
        speaker.setName("홍길동");
        speaker.setEmail("seungho.hyun@navercorp.com");
        speaker.setDescription("안드로이드 전문가입니다.");
        speaker.setPhone("010-1234-1234");
        speaker.setImg("img");
        speaker.setSectionIdx(1);
        return speaker;
    }
}
