package com.henning.pieter.instantinterval;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PodHistory {
    private static final Logger logger = Logger.getLogger("PH");
    public Tag[] tags = new Tag[5];
    int last = 1;
    String id;

    PodHistory(String id, long time, int rssi) {
        this.id = id;
        tags[0] = new Tag(id, time, rssi);
        last = 1;
    }

    // newest valye always at 0
    public void update(long time, int rssi) {
//        logger.log(Level.INFO,"@@@@@ id @@@@ " + id);

        // shift tags
        for (int i = last; i >=  1; i--) {
            tags[i] = tags[i - 1];
        }
        int avr = (tags[1].avr + rssi) / 2;
        tags[0] = new Tag(id, time, rssi, avr);
        if (last < 4) {
            last++;
        }
        logger.log(Level.INFO,(tags[0].id + " : " + tags[0].ts + " " + tags[0].avr));
        System.out.println(tags[0].ts + " " + tags[0].avr);
        //isMax();
    }

    // newest tag always at 0
    public Tag getMax() {
        if (last > 2) {
            if ((tags[2].avr < tags[1].avr) && (tags[0].avr < tags[1].avr)) {
                System.out.println("max : " + tags[1].pwr  + " at time " + tags[1].ts);
                return tags[1];
            }
        }
        return null;
    }
}