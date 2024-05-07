
public class Client {

    public static void main(String[] args){

                // Initialize TaskSchedule
                TaskSchedule taskSchedule = TaskSchedule.getInstance();

                // Create tasks
                Task meeting = new RecurringTask();
                meeting.setName("Weekly Meeting");
                meeting.setType("Recurring");
                meeting.setStartDate(20240101);
                meeting.setEndDate(20240131);
                meeting.setStartTime(10.0f);
                meeting.setDuration(2.0f);
                meeting.setFrequency(7); // Recurring weekly
        
                Task appointment = new TransientTask();
                appointment.setName("Doctor Appointment");
                appointment.setType("Transient");
                appointment.setStartDate(20240115);
                appointment.setStartTime(14.0f);
                appointment.setDuration(1.0f);
        
                Task cancellation = new AntiTask();
                cancellation.setName("Meeting Cancellation");
                cancellation.setType("Anti");
                cancellation.setStartDate(20240108); // Cancels the meeting on this date
                cancellation.setStartTime(10.0f);
                cancellation.setDuration(2.0f);
        
                // Add tasks to the schedule
                taskSchedule.addTask(meeting);
                taskSchedule.addTask(appointment);
                taskSchedule.addTask(cancellation);
        
                // Initialize ScheduleViewer and display schedules
                ScheduleViewer viewer = ScheduleViewer.getInstance();
        
                // Print specific day schedules
                System.out.println("Schedule for January 1, 2024:");
                viewer.printDaySchedule(20240101);
        
                System.out.println("\nSchedule for January 15, 2024:");
                viewer.printDaySchedule(20240115);
        
                // Print a week schedule
                System.out.println("\nSchedule for the first week of January 2024:");
                viewer.printWeekSchedule(20240101);
        
                // Print a month schedule
                System.out.println("\nSchedule for January 2024:");
                viewer.printMonthSchedule(20240101);

    }
}
