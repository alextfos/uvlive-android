package es.uv.uvlive.utils;

public class NumberFormatUtils {

    private NumberFormatUtils() {
        throw new RuntimeException("Class can't be instatiated");
    }

    public static int longToInt( long longValue) {
        if (longValue < Integer.MIN_VALUE) {
            return Integer.MIN_VALUE;
        } else if (longValue > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        } else {
            return (int) longValue;
        }
    }
}
