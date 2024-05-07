
public class Client {

    public static void main(String[] args){

                // Create tasks
                RecurringTask recurringTask = new RecurringTask();
                recurringTask.setName("Team Meeting");
                recurringTask.setType("Recurring");
                recurringTask.setStartTime(14.0f); // 2 PM
                recurringTask.setDuration(2.0f); // 2 hours duration
                recurringTask.setStartDate(20230101); // January 1, 2023
                recurringTask.setEndDate(20231231); // December 31, 2023
                recurringTask.setFrequency(7); // Weekly
        
                TransientTask transientTask = new TransientTask();
                transientTask.setName("Project Deadline");
                transientTask.setType("Transient");
                transientTask.setStartTime(17.0f); // 5 PM
                transientTask.setDuration(1.0f); // 1 hour duration
                transientTask.setStartDate(20230110); // January 10, 2023
        
                AntiTask antiTask = new AntiTask();
                antiTask.setName("Cancel Meeting");
                antiTask.setType("Anti");
                antiTask.setStartTime(14.0f); // 2 PM same as meeting time
                antiTask.setDuration(2.0f); // 2 hours, cancels out the meeting
                antiTask.setStartDate(20230108); // January 8, 2023, assuming this was a meeting day
        
                // Add tasks to TaskSchedule
                TaskSchedule schedule = TaskSchedule.getInstance();
                schedule.addTask(recurringTask);
                schedule.addTask(transientTask);
                schedule.addTask(antiTask);
        
                // Initialize ScheduleViewer and print schedules
                ScheduleViewer viewer = ScheduleViewer.getInstance();
                System.out.println("Day Schedule for January 1, 2023:");
                viewer.printDaySchedule(20230101);
        
                System.out.println("\nWeek Schedule for first week of January 2023:");
                viewer.printWeekSchedule(20230101);
        
                System.out.println("\nMonth Schedule for January 2023:");
                viewer.printMonthSchedule(1);

    }
}
