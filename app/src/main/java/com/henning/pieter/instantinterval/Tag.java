package com.henning.pieter.instantinterval;

public class Tag {
    public long ts;  // timestamp in ms
    public int pwr;
    public int avr; //
    public String id;

    Tag(String id, long timestamp , int power) {
        this.ts = timestamp;
        this.pwr = power;
        this.avr = power;
        this.id  = id;
    }

    Tag(String id, long timestamp , int power , int avr) {
        this.ts = timestamp;
        this.pwr = power;
        this.avr = avr;
        this.id  = id;
    }
}
