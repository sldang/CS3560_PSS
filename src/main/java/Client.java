import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        // user select menu
        int option;
        try (Scanner scnr = new Scanner(System.in)) {
            // loops until you choose the option to exit the menu
            for (;;) {
                System.out.println("PSS Client Menu Selection Screen");
                System.out.println("1 = Create a task");
                System.out.println("2 = View a task");
                System.out.println("3 = Delete a task");
                System.out.println("4 = Edit a task");
                System.out.println("5 = Write the schedule to a json file");
                System.out.println("6 = Read the schedule to a json file");
                System.out.println("7 = View or write the schedule for one day");
                System.out.println("8 = View or write the schedule for one week");
                System.out.println("9 = View or write the schedule for one month");
                System.out.println("10 to exit the menu.");
                System.out.print("Enter option: ");
                option = scnr.nextInt();
                System.out.println("");

                if (option == 10) {
                    System.exit(0);
                    break;
                } else if (option == 1) {
                    // addTask();
                    Scanner scanner = new Scanner(System.in);
                    // Initialize FileSaving instance
                    FileSaving fileSaving = FileSaving.getInstance();
                    // Create a list of tasks
                    List<Task> tasks = new ArrayList<>();
                    // Prompt user to enter task details until they indicate they are done
                    boolean done = false;
                    while (!done) {
                        Task task = createTaskFromUserInput(scanner);
                        if (task != null) {
                            tasks.add(task);
                        }

                        System.out.print("Do you want to add another task? (yes/no): ");
                        String addAnother = scanner.nextLine();
                        System.out.println();
                        if (!addAnother.equalsIgnoreCase("yes")) {
                            done = true;
                        }
                    }
                    // Write tasks to a JSON file
                    String fileName = "Set1.json";
                    fileSaving.writeTasksToFile(fileName, tasks);

                    // Read tasks from the file
                    System.out.println("Reading tasks from file:");
                    List<Task> readTasks = fileSaving.readFromFile("Set1.json");

                    // Assuming a method exists to print the details of the tasks
                    if (readTasks != null) {
                        for (Task task : readTasks) {
                            System.out.println("Task: " + task.getName() + ", Type: " + task.getType() +
                                    ", Start Date: " + task.getStartDate() + ", Duration: " + task.getDuration());
                        }
                    } else {
                        System.out.println("No tasks were read from the file or the file is empty.");
                    }

                    System.out.println("Task(s) added");
                    System.out.println("-----------------------");
                    System.out.println();
                } else if (option == 2) {
                    // viewTask();
                    System.out.println("Task viewed");
                    System.out.println("-----------------------");
                    System.out.println();
                } else if (option == 3) {
                    // deleteTask();
                    System.out.println("Task deleted");
                    System.out.println("-----------------------");
                    System.out.println();
                } else if (option == 4) {
                    // editTask();
                    System.out.println("Task edited");
                    System.out.println("-----------------------");
                    System.out.println();
                    // break;
                } else if (option == 5) {
                    // writeTasksToFile();
                    // Write the schedule to a json file
                    System.out.println("Schedule written to the json file");
                    System.out.println("-----------------------");
                    System.out.println();
                } else if (option == 6) {
                    // readFromFile();
                    // read the schedule to a json file
                    System.out.println("Schedule read to the json file");
                    System.out.println("-----------------------");
                    System.out.println();
                } else if (option == 7) {
                    // printDaySchedule();
                    // view or write the schedule for one specific day
                    System.out.println("This is your specified day schedule");
                    System.out.println("-----------------------");
                    System.out.println();
                    // break;
                } else if (option == 8) {
                    // printWeekSchedule();
                    // view or write the schedule for one specific week
                    System.out.println("This is your specified week schedule");
                    System.out.println("-----------------------");
                    System.out.println();
                    // break;
                } else if (option == 9) {
                    // printMonthSchedule();
                    // view or write the schedule for one specific month
                    System.out.println("This is your specified month schedule");
                    System.out.println("-----------------------");
                    System.out.println();
                    // break;
                }
            }
        }
    }

    // Method to create a task based on user input
    private static Task createTaskFromUserInput(Scanner scanner) {
        TaskFactory taskFactory = TaskFactory.getInstance();
    
        // Prompt user to choose task type
        System.out.println("Choose task type:");
        System.out.println("1. Recurring Task");
        System.out.println("2. Transient Task");
        System.out.println("3. AntiTask");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
    
        String type;
        switch (choice) {
            case 1:
                type = "RecurringTask";
                break;
            case 2:
                type = "TransientTask";
                break;
            case 3:
                type = "AntiTask";
                break;
            default:
                System.out.println("Invalid choice. Task not created.");
                return null;
        }
    
        // Create task based on user input
        Task task = taskFactory.createTask(type);
        if (task != null) {
            System.out.print("Enter task name: ");
            task.setName(scanner.nextLine());
    
            System.out.print("Enter task type: ");
            task.setType(scanner.nextLine());
    
            System.out.print("Enter start date (YYYYMMDD): ");
            task.setStartDate(scanner.nextInt());
            scanner.nextLine(); // Consume newline
    
            System.out.print("Enter start time (24-hour format): ");
            task.setStartTime(scanner.nextFloat());
            scanner.nextLine(); // Consume newline
    
            System.out.print("Enter duration (in hours): ");
            task.setDuration(scanner.nextFloat());
            scanner.nextLine(); // Consume newline
    
            // Additional inputs based on task type
            if (task instanceof RecurringTask) {
                RecurringTask recurringTask = (RecurringTask) task;
                System.out.print("Enter end date (YYYYMMDD): ");
                recurringTask.setEndDate(scanner.nextInt());
                scanner.nextLine(); // Consume newline
    
                System.out.print("Enter frequency (days between occurrences): ");
                recurringTask.setFrequency(scanner.nextInt());
                scanner.nextLine(); // Consume newline
            }
        } else {
            System.out.println("Failed to create task. Task type not recognized.");
        }
    
        return task;
    }
}