package me.sildev.zoopr.utils;

public class MillisecondsToTime {

    public static String getTime(long time) {
        String timeString;
        double mintutes = time / (1000*60);
        double hours = time / (1000*60*60);
        if (hours <= 0) {
            timeString = mintutes + " minutes";
        } else {
            timeString = hours + " hours";
        }

        return timeString;
    }
}
