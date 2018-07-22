package com.henning.pieter.instantinterval;

public class IntervalItem {

        public final String id;
        public final String startName;
        public final String endName;
        public final Long timeMs;
        public final String  time;

        public IntervalItem(int id, String startName, String endName, Long timeMs) {
            this.id = String.valueOf(id);
            this.startName = startName;
            this.endName = endName;
            this.timeMs = timeMs;
            time = makeTimeString();
        }

        private String makeTimeString(){
            int sec = (int) (timeMs / 1000);
            int ms = (int)(timeMs % 1000);
            int min = 0;
            int hour = 0;

            if (sec > 60) {
                min = sec / 60;
                sec = sec % 60;
            }

            if (min > 60) {
                hour = min / 60;
                min = min % 60;
            }

            return   convertHour(hour) + convertMin(hour,min) + convertTime(sec) + "." + convertMs(ms);
        }

    /*
    if no hours present do not incude hours
     */
    private String convertHour(int h){
        if (h == 0 ){
            return "";
        } else {
            return convertTime(h) + "h ";
        }

    }

    /*
    if min =0 do not include min unless hours are not also 0
     */
    private String convertMin(int h, int m){
        if (h == 0 ){
            if (m == 0){
                return "";
            } else {
                return convertTime(m) + "m ";
            }
        } else {
            return convertTime(m) + "m ";
        }

    }

    /*
    asd leading zeros to min and hours
     */
    private String convertTime(int t) {
        String res;
        if (t < 10){
            res = "0"+ String.valueOf(t);
        } else {
            res = String.valueOf(t);
        }
        return res;
    }

    private String convertMs(int ms){
        String miliS;
        if (ms < 10){
            miliS = "00"+ String.valueOf(ms);
        } else if (ms < 100) {
            miliS = "0"+ String.valueOf(ms);
        } else  {
            miliS = String.valueOf(ms);
        }
        return miliS;
    }



        @Override
        public String toString() {
            return startName + "-" + endName + " : " + time;
        }
    }