package es.uv.uvlive.utils;


import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtils {

    private DateUtils() {
        throw new RuntimeException("Class can't be instatiated");
    }

    public static String timestampToStringDate(long timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        return format1.format(calendar.getTime());
    }
}
