package com.project.ece651.webapp.utils;

import java.sql.Date;
import java.util.Calendar;

public class DateUtils {
    public static Date calDate(int year, int month, int day) {
        // set up a java.sql.data based on the given year, month and day
        // reference https://stackoverflow.com/questions/22539055/construct-java-sql-date-from-year-month-and-date-in-integer
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        // the -1 is used because the Calendar.JANUARY is actually 0, so if we input 1 to represent January, the -1 is needed
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DATE, day);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return new Date(cal.getTimeInMillis());
    }
}
