    import java.util.List;

    public class ScheduleViewer {
        private static ScheduleViewer instance;

        // Constructor
        private ScheduleViewer() {

        }

        public static ScheduleViewer getInstance() {
            if (instance == null) {
                instance = new ScheduleViewer();
            }
            return instance;
        }

        // Print the schedule for a single day
        public void printDaySchedule(int date) {
            int[] timeFrame = DateCalculator.getWeekDates(date);
            List<Task> dayTasks = TaskSchedule.getInstance().getTimeFrame(date, date);
            System.out.println("Day Schedule for " + date + ":");
            printTasks(dayTasks);
        }

        // Print the schedule for a whole week
        public void printWeekSchedule(int date) {
            int[] timeFrame = DateCalculator.getWeekDates(date);
            List<Task> weekTasks = TaskSchedule.getInstance().getTimeFrame(timeFrame[0], timeFrame[1]);
            System.out.println("Week Schedule starting from " + timeFrame[0] + " to " + timeFrame[1] + ":");
            printTasks(weekTasks);
        }

        // Print the schedule for a specified period
        public void printPeriodSchedule(int begin, int end) {
            List<Task> periodTasks = TaskSchedule.getInstance().getTimeFrame(begin, end);
            System.out.println("Period Schedule from " + begin + " to " + end + ":");
            printTasks(periodTasks);
        }

        // Print the schedule for a whole month
        // Updated method to include a year parameter
        public void printMonthSchedule(int month, int year) {
            int[] timeFrame = DateCalculator.getMonthDates(month, year);  // Now correctly passes both month and year
            List<Task> tasksInFrame = TaskSchedule.getInstance().getTimeFrame(timeFrame[0], timeFrame[1]);
            System.out.println("Month Schedule for " + month + "/" + year + ":");
            printTasks(tasksInFrame);
        }


        // Print the tasks
        public void printTasks(List<Task> tasks) {
            if (tasks.isEmpty()) {
                System.out.println("No tasks scheduled.");
                return;
            }
            for (Task task : tasks) {
                System.out.println("Task: " + task.getName() + " - Date: " + task.getDateInstance() + " - Time: " + task.getStartTime());
            }
        }
    }



