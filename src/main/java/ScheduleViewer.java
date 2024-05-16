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
        public List<Task> printDaySchedule(int date) {
            List<Task> dayTasks = TaskSchedule.getInstance().getTimeFrame(date, date);
            System.out.println("Day Schedule for " + date + ":");
            printTasks(dayTasks, true);
            return dayTasks;
        }

        // Print the schedule for a whole week
        public List<Task> printWeekSchedule(int date) {
            int[] timeFrame = DateCalculator.getWeekDates(date);
            List<Task> weekTasks = TaskSchedule.getInstance().getTimeFrame(timeFrame[0], timeFrame[1]);
            System.out.println("Week Schedule starting from " + timeFrame[0] + " to " + timeFrame[1] + ":");
            printTasks(weekTasks, true);
            return weekTasks;
        }

        // Print the schedule for a specified period
        public List<Task> printPeriodSchedule(int begin, int end) {
            List<Task> periodTasks = TaskSchedule.getInstance().getTimeFrame(begin, end);
            System.out.println("Period Schedule from " + begin + " to " + end + ":");
            printTasks(periodTasks, true);
            return periodTasks;
        }

        // Print the schedule for a whole month
        // Updated method to include a year parameter
        public List<Task> printMonthSchedule(int month, int year) {
            int[] timeFrame = DateCalculator.getMonthDates(month, year);  // Now correctly passes both month and year
            List<Task> tasksInFrame = TaskSchedule.getInstance().getTimeFrame(timeFrame[0], timeFrame[1]);
            System.out.println("Month Schedule for " + month + "/" + year + ":");
            printTasks(tasksInFrame, true);
            return tasksInFrame;
        }


        // Print the tasks
        public void printTasks(List<Task> tasks, boolean instanced) {
            if (tasks.isEmpty()) {
                System.out.println("No tasks scheduled.");
                return;
            }
            for (Task task : tasks) {
                if (instanced){
                    System.out.println(task.toStringInstance());
                } else {
                    System.out.println(task.toString());
                }


                System.out.println("-----------------------");
            }
        }
        
        private String formatDate(int date) {
            // Assuming date is in YYYYMMDD format
            return date / 10000 + "-" + (date % 10000) / 100 + "-" + date % 100;
        }
        
        private String formatTime(float time) {
            int hours = (int) time;
            int minutes = (int) ((time - hours) * 60);
            return String.format("%02d:%02d", hours, minutes);
        }        
    }



