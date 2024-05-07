import java.io.IOException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
       
        // user select menu
            int option;
            try (Scanner scnr = new Scanner(System.in)) {
                // loops until you choose the option to exit the menu
                for ( ; ; )
                {   
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
            
                   
                        if (option == 0) {
                            break;
                        }
                        else if (option == 1) {
                        //    addTask();
                            System.out.println("Task added");
                            System.out.println();
                        }
                        else if (option == 2) {
                           // viewTask();
                           System.out.println("Task viewed");
                            System.out.println();
                        }
                        else if (option == 3) {
                            // deleteTask();
                            System.out.println("Task deleted");
                            System.out.println();
                        }
                        else if (option == 4) {
                            // editTask();
                            System.out.println("Task edited");
                            System.out.println();
                            // break;
                        }
                        else if (option == 5) {
                            // writeTasksToFile();
                            // Write the schedule to a json file
                            System.out.println("Schedule written to the json file");
                            System.out.println();
                        }
                        else if (option == 6) {
                            // readFromFile();
                            // read the schedule to a json file
                            System.out.println("Schedule read to the json file");
                            System.out.println();
                        }
                        else if (option == 7) {
                            // printDaySchedule();
                            // view or write the schedule for one specific day
                            System.out.println("This is your specified day schedule");
                            System.out.println();
                            // break;
                        }
                        else if (option == 8) {
                            // printWeekSchedule();
                            // view or write the schedule for one specific week
                            System.out.println("This is your specified week schedule");
                            System.out.println();
                            // break;
                        }
                        else if (option == 9) {
                             // printMonthSchedule();
                            // view or write the schedule for one specific month
                            System.out.println("This is your specified month schedule");
                            System.out.println();
                            // break;
                        }
                        else if(option == 10)
                            {
                                System.exit(0);
                                break;
                            }
                    }
                }
            
                
        
        }
    }