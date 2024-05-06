import java.util.List;

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

    //FIXME: Do we want to have methods (inclusive, inclusive)? - Alex

    //Note: Dates are integers in the form of YYYYMMDD

    public void printDaySchedule(int date) {
        List<Task> dayTasks = TaskSchedule.getInstance().getTimeFrame(date, date);
    }

    // Assumes date is within week
    public void printWeekSchedule(int date) {
        int[] timeFrame = DateCalculator.getWeekDates(date);
        List<Task> weekTasks = TaskSchedule.getInstance().getTimeFrame(timeFrame[0], timeFrame[1]);
    }

    public void printPeriodSchedule(int begin, int end) {
        List<Task> periodTasks = TaskSchedule.getInstance().getTimeFrame(begin, end);
    }

    //Note: Dates are integers in the form of YYYYMMDD
    //Month assumes current currentYear
    public void printMonthSchedule(int month) {
        int[] timeFrame = DateCalculator.getMonthDates(month);
        List<Task> tasksInFrame = TaskSchedule.getInstance().getTimeFrame(timeFrame[0], timeFrame[1]);
    }

    //TODO: work on this implementation
    private void printTasks(List<Task> tasks){
        for (Task task: tasks){
            System.out.println(task.getName());
        }
    }

}

