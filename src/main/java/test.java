import java.util.List;

public class test {

    public static void main(String[] args){
       
        TaskSchedule schedule = TaskSchedule.getInstance();

        // Example tasks
        Task task1 = new RecurringTask();
        task1.setName("Weekly Team Meeting");
        task1.setType("Meeting");
        task1.setStartDate(20230101);
        task1.setEndDate(20230131);
        task1.setStartTime(9.0f);
        task1.setDuration(1.0f);
        task1.setFrequency(1);

        Task task2 = new TransientTask();
        task2.setName("Doctor Appointment");
        task2.setType("Appointment");
        task2.setStartDate(20230410);
        task2.setStartTime(14.0f);
        task2.setDuration(0.5f);

        Task task3 = new AntiTask();
        task3.setName("Meeting Cancellation");
        task3.setType("Cancellation");
        task3.setStartDate(20230108);
        task3.setStartTime(9.0f);
        task3.setDuration(1.0f);

        Task task4 = new TransientTask();
        task4.setName("Shopping");
        task4.setType("Shopping");
        task4.setStartDate(20230410);
        task4.setStartTime(17.0f);
        task4.setDuration(1.0f);

        // Adding tasks to the schedule
        System.out.println("Adding Task 1: " + task1.getName());
        schedule.addTask(task1);

        System.out.println("Adding Task 2: " + task2.getName());
        schedule.addTask(task2);

        System.out.println("Adding Task 4: " + task4.getName());
        schedule.addTask(task4);

        System.out.println("\nScheduled Tasks: ");
        List<Task> allTasks = schedule.listAllTasks();
        for (Task task : allTasks) {
            System.out.println("Task Name: " + task.getName());
            System.out.println("Type: " + task.getType());
            System.out.println("Start Date: " + task.getStartDate());
            System.out.println("End Date: " + task.getEndDate());
            System.out.println("Start Time: " + task.getStartTime());
            System.out.println("Duration: " + task.getDuration());
            System.out.println("Frequency: " + (task.getFrequency() > 0 ? task.getFrequency() + " days " : "N/A"));
            System.out.println("-----------------------");
        }

        System.out.println("Removing Task 2: " + task2.getName());
        schedule.removeTask(task2);

        //Printing all tasks
        System.out.println("\nScheduled Tasks: ");
        allTasks = schedule.listAllTasks();
        for (Task task : allTasks) {
            System.out.println("Task Name: " + task.getName());
            System.out.println("Type: " + task.getType());
            System.out.println("Start Date: " + task.getStartDate());
            System.out.println("End Date: " + task.getEndDate());
            System.out.println("Start Time: " + task.getStartTime());
            System.out.println("Duration: " + task.getDuration());
            System.out.println("Frequency: " + (task.getFrequency() > 0 ? task.getFrequency() + " days " : "N/A"));
            System.out.println("-----------------------");
        }

    }

    public static int addition(int x, int y) {
        return x + y;
    }
}
