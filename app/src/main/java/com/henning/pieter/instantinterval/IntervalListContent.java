package com.henning.pieter.instantinterval;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 */
public class IntervalListContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<IntervalItem> ITEMS = new ArrayList<IntervalItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
//    public static final Map<String, IntervalItem> ITEM_MAP = new HashMap<String, IntervalItem>();

    private static final int COUNT = 3;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(IntervalItem item) {
        ITEMS.add(0,item);

//        ITEM_MAP.put(item.id, item);
    }

    private static IntervalItem createDummyItem(int position) {
        Random rand = new Random();
        return new IntervalItem(position, "Start " + position, "end", rand.nextLong());
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }


}
