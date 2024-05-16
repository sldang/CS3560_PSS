import java.io.IOException;
// import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        // user select menu
        int option;
        // creating a schedule object to use
        TaskSchedule schedule = TaskSchedule.getInstance();
        Scanner scanner = new Scanner(System.in);
        FileSaving fileSaving = FileSaving.getInstance();

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
                System.out.println("11 [DEBUG] to view all added tasks");
                System.out.print("Enter option: ");
                option = scnr.nextInt();
                System.out.println("");

                if (option == 10) {
                    System.exit(0);
                    break;
                } else if (option == 1) {

                // Create task from user input. Asks until user is done creating all the tasks they want
                boolean done = false;
                while (!done) {
                    Task task = createTaskFromUserInput(scanner);
                    if (task != null) {
                        schedule.addTask(task);
                    }

                    System.out.print("Do you want to add another task? (yes/no): ");
                    String addAnother = scanner.nextLine().toLowerCase(); // Convert input to lowercase for comparison
                    System.out.println();
                    if (!addAnother.equals("yes")) {
                        done = true;
                    }
                }
                    System.out.println("Task(s) added");
                    System.out.println("-----------------------");
                    System.out.println();
                } else if (option == 2) {
                    // THIS IS TO VIEW 1 SPECIFIC TASK details
                    System.out.println("\nTask Search: ");
                    System.out.print("Enter the name of the task to be viewed: ");
                    String taskNameToView = scanner.nextLine();
                    Task taskToView = schedule.findTaskByName(taskNameToView);
                    if (taskToView != null) {
                        System.out.println("");
                        System.out.println("Here are the specific task details:");
                        System.out.println("Task Name: " + taskToView.getName());
                        System.out.println("Type: " + taskToView.getType());
                        System.out.println("Start Date: " + taskToView.getStartDate());
                        System.out.println("End Date: " + taskToView.getEndDate());
                        System.out.println("Start Time: " + taskToView.getStartTime());
                        System.out.println("Duration: " + taskToView.getDuration());
                        System.out.println("Frequency: " + (taskToView.getFrequency() > 0 ? taskToView.getFrequency() + " days " : "N/A"));
                        System.out.println("Task viewed!");
                    } else {
                        System.out.println("Task does not exist.");
                    }
                    System.out.println("-----------------------");
                    System.out.println();
                } else if (option == 3) {
                    // schedule.removeTask(schedule.findTaskByName("Intern Interview"));
                    // System.out.println("Task deleted");
                    System.out.print("Enter the name of the task to be deleted: ");
                        String taskNameToDelete = scanner.nextLine();
                        Task taskToDelete = schedule.findTaskByName(taskNameToDelete);
                        if (taskToDelete != null) {
                            schedule.removeTask(taskToDelete);
                        } else {
                            System.out.println("Task does not exist.");
                        }
                    System.out.println("-----------------------");
                    System.out.println();
                } else if (option == 4) {
                      // Prompt for the name of the task to update
                      System.out.print("Enter the name of the task to update: ");
                      String taskNameToUpdate = scanner.nextLine();

                      // Find the existing task
                      Task existingTask = schedule.findTaskByName(taskNameToUpdate);
                      if (existingTask == null) {
                         System.out.println("Task not found.");
                      } else {
                         // Display current task details
                         System.out.println("Current task details:");
                         System.out.println("Name: " + existingTask.getName());
                         System.out.println("Start Date: " + existingTask.getStartDate());
                         System.out.println("End Date: " + existingTask.getEndDate());
                         System.out.println("Start Time: " + existingTask.getStartTime());
                         System.out.println("Duration: " + existingTask.getDuration());
                         System.out.println("Frequency: " + existingTask.getFrequency());
                         System.out.println();
                         System.out.println("Updated Task Details:");
                         System.out.println();
  
                         // Create a new task with updated details from user input
                        Task updatedTask = createTaskFromUserInput(scanner);

                        // Update task in the schedule
                        if (!schedule.checkForOverlaps(updatedTask)) {
                           schedule.updateTask(taskNameToUpdate, updatedTask);
                           System.out.println();
                           System.out.println("Task updated successfully.");
                        } else {
                            System.out.println();
                           System.out.println("Task update failed.");
                         }
                      }
                      System.out.println("-----------------------");
                      System.out.println();
                } else if (option == 5) {
                    // writeTasksToFile();
                    // Write the schedule to a json file
                     // Save tasks to file
                    System.out.print("Enter filename to save tasks: (Make sure to write your new schedule to a new json file and NOT to an existing json file.)");
                    String saveFileName = scanner.nextLine();
                    fileSaving.writeTasksToFile(saveFileName, schedule.listAllTasks());
                    System.out.println("Tasks saved to " + saveFileName);
                    System.out.println("-----------------------");
                    System.out.println();
                } else if (option == 6) {
                    // readFromFile();
                    // read the schedule to a json file
                    System.out.print("Enter filename to load tasks from: ");
                    String loadFileName = scanner.nextLine();
                    List<Task> readTasks = fileSaving.readFromFile(loadFileName);
                    // Print the details of the tasks
                    if (readTasks != null && !readTasks.isEmpty()) {
                        for (Task task : readTasks) {
                            try {
                                TaskSchedule.getInstance().addTask(task);
                            } catch (Exception e){
                                System.out.println("u stupid");
                            }
                            System.out.println("Task: " + task.getName() + ", Type: " + task.getType() +
                                ", Start Date: " + task.getStartDate() + ", Duration: " + task.getDuration());
                        }
                    } else {
                        System.out.println("No tasks were read from the file or the file is empty.");
                    }
                    System.out.println("-----------------------");
                    System.out.println();
                } else if (option == 7) {
                    // printDaySchedule();
                    // view or write the schedule for one specific day
                    System.out.print("Enter the date (YYYYMMDD) for the day's schedule: ");
                    int date = scnr.nextInt();
                    scnr.nextLine();  // consume newline left-over
                    ScheduleViewer.getInstance().printDaySchedule(date);
                    System.out.println("This is your specified day schedule");
                    System.out.println("-----------------------");
                    System.out.println();
                } else if (option == 8) {
                    // printWeekSchedule();
                    // view or write the schedule for one specific week
                    System.out.print("Enter the date (YYYYMMDD) to start the week's schedule: ");
                    int weekDate = scnr.nextInt();
                    scnr.nextLine();  // consume newline left-over
                    ScheduleViewer.getInstance().printWeekSchedule(weekDate);
                    System.out.println("This is your specified week schedule");
                    System.out.println("-----------------------");
                    System.out.println();
                } else if (option == 9) {
                    System.out.println("Enter the year and month as YYYYMM (e.g., 202204): ");
                    if (scnr.hasNextLine()) {  // Check if there's a next line to consume
                        scnr.nextLine();  // Consume any leftover newline characters in the buffer
                    }
                    String yearMonthInput = scnr.nextLine().trim();  // Read the actual input
                
                    if (yearMonthInput.length() == 6) {
                        try {
                            int year = Integer.parseInt(yearMonthInput.substring(0, 4));
                            int month = Integer.parseInt(yearMonthInput.substring(4, 6));
                
                            if (month >= 1 && month <= 12) {
                                ScheduleViewer.getInstance().printMonthSchedule(month, year);
                                System.out.println("This is your specified month schedule");
                                System.out.println("-----------------------");
                                } 
                            } catch (NumberFormatException e) {
                            System.out.println("Invalid input format. Please enter the date in YYYYMM format.");
                        }
                    } else {
                        System.out.println("Invalid input length. Please enter exactly 6 digits for the year and month (YYYYMM).");
                    }
                } else if (option == 11) {
                    System.out.println("Printing all tasks");
                    List<Task> allTasks = schedule.listAllTasks();
                    ScheduleViewer.getInstance().printTasks(allTasks);
                }
            }
        }
    }

    // Method to create a task based on user input
    private static Task createTaskFromUserInput(Scanner scanner) {
        TaskFactory taskFactory = TaskFactory.getInstance();
        TaskSchedule schedule_ref = TaskSchedule.getInstance();

        String name = "";
        String type = null;

        boolean isUnique;
        do {
            System.out.print("Enter task name: ");
            name = scanner.nextLine();
            isUnique = schedule_ref.findTaskByName(name) == null;
            if (!isUnique) {
                System.out.println("A task with this name already exists. Please enter a unique name.");
            }
        } while (!isUnique);

        System.out.print("Enter task type: ");
        type = scanner.nextLine();

        Task task = taskFactory.createTask(TaskFactory.getTranslation(type));
        if (task != null) {
            
            task.setName(name);

            task.setType(type);

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
