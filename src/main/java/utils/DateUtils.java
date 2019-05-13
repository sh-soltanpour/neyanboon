package utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    public static Date addHours(Date date, int hours){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, hours);
        return calendar.getTime();
    }
}
