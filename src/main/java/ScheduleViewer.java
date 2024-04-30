public class ScheduleViewer {
    private static ScheduleViewer instance;

    // constructor
    private ScheduleViewer(){

    }

    public static ScheduleViewer getInstance() {
        if (instance == null) {
            instance = new ScheduleViewer();
        }
        return instance;
    }

    public void printDaySchedule(int date) {

    }

    public void printWeekSchedule(int date) {

    }

    public void printPeriodSchedule(int begin, int end) {

    }

    public void printMonthSchedule(int month) {
        
    }

}

