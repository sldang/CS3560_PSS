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

    public static int[] getMonthDates(int month, int year) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = new GregorianCalendar();

        // Set the calendar to the first day of the given month and year.
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);  // Month value in Calendar is 0-based (January = 0).
        calendar.set(Calendar.DAY_OF_MONTH, 1);  // Set to the first day of the month.

        // Format the start date and store it.
        String start = sdf.format(calendar.getTime());

        // Move the calendar to the last day of the same month.
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));  // Last day of the month.
        
        // Format the end date and store it.
        String end = sdf.format(calendar.getTime());

        // Parse the start and end dates back into integers and return them.
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

    public static boolean isValidDate(int date){
        Calendar calendar = new GregorianCalendar();
        String dateString = String.valueOf(date);

        //FIXME Note: Calendar uses 0-11 Format for Month when setting date
        // Uses 1-31 for Day
        try {
            calendar.set(
                    Integer.parseInt(dateString.substring(0, 4)),
                    Integer.parseInt(dateString.substring(4, 6)) - 1,
                    Integer.parseInt(dateString.substring(6, 8)));

            int month = Integer.parseInt(dateString.substring(4, 6)) - 1;
            int day = Integer.parseInt(dateString.substring(6, 8));

            if (month >= 0 && month <= 11) {
                if (day >= 1 && day <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                    return true;
                }
            }
        } catch (Exception e){
            // Calendar instantiation failed.
        }
        System.out.println("Not a valid date!");
        return false;
    }
}
