import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

import static java.util.Calendar.MONTH;

public class DateCalculator {

    /**
     * Returns dates from Sunday - Saturday
     * @param date
     */
    public static int[] getWeekDates(int date){
        //https://stackoverflow.com/questions/3941700/how-to-get-dates-of-a-week-i-know-week-number
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = new GregorianCalendar();
        String dateString = String.valueOf(date);

        //FIXME Note: Calendar uses 0-11 Format for Month when setting date
        // Uses 1-31 for Day
        calendar.set(
                Integer.parseInt(dateString.substring(0,4)),
                Integer.parseInt(dateString.substring(4,6)) - 1,
                Integer.parseInt(dateString.substring(6,8)));
        int week = calendar.getWeekYear();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        String start = sdf.format(calendar.getTime());
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        String end = sdf.format(calendar.getTime());

        int startDate = Integer.parseInt(start);
        int endDate = Integer.parseInt(end);

        return new int[]{startDate, endDate};
    }

    public static int[] getMonthDates(int month){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();

        //FIXME Note: Calendar uses 0-11 Format for Month when setting date
        calendar.set(Calendar.YEAR, 2024);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        String start = sdf.format(calendar.getTime());
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getMaximum(Calendar.DAY_OF_MONTH));
        String end = sdf.format(calendar.getTime());

        System.out.println(start);
        System.out.println(end);

        int startDate = Integer.parseInt(start);
        int endDate = Integer.parseInt(end);

        return new int[]{startDate, endDate};
    }

    public static int addDaysToDate(int date, int days){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = new GregorianCalendar();
        String dateString = String.valueOf(date);

        //FIXME Note: Calendar uses 0-11 Format for Month when setting date
        // Uses 1-31 for Day
        calendar.set(
                Integer.parseInt(dateString.substring(0,4)),
                Integer.parseInt(dateString.substring(4,6)) - 1,
                Integer.parseInt(dateString.substring(6,8)));
        calendar.add(Calendar.DAY_OF_WEEK, days);
        return Integer.parseInt(sdf.format(calendar.getTime()));
    }

    public static String getMonthString(int month){
        String monthString = "";
        if (month < 10){
            monthString += "0";
        }
        return monthString + month;
    }
}
