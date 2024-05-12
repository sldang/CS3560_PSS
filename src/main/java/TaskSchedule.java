import static org.junit.jupiter.api.DynamicTest.stream;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TaskSchedule {

    private ScheduleLinkedList schedule = new ScheduleLinkedList(); // This is a calendar map that represents each event in time
    private List<Task> tasksGeneral = new ArrayList<>(); // This is a generalized list of all events (Recurrings are only listed once)


    private static TaskSchedule instance;

    private TaskSchedule(){

    }

    public static TaskSchedule getInstance(){
        if (instance == null){
            instance = new TaskSchedule();
        }
        return instance;
    }

    //FIXME: This is a really long method and should be split up - Alex
    public void addTask(Task task){
        if (!checkForOverlaps(task)){
            tasksGeneral.add(task);
            int frequency = task.getFrequency();
            int iteratedDate = task.getStartDate();
            ScheduleNode iteratedEvent = schedule.getHead();
            ScheduleNode previousEvent = null;
            while (iteratedDate <= task.getEndDate()){
                Task copiedTaskEvent = task.getCopyOf();
                copiedTaskEvent.setDateInstance(iteratedDate);
                ScheduleNode nodeToAdd = new ScheduleNode(copiedTaskEvent);
                nodeToAdd.setTask(copiedTaskEvent);

                while (iteratedEvent != null && iteratedEvent.getTask().getDateInstance() <= iteratedDate){
                    if (iteratedEvent.getTask().getDateInstance() == iteratedDate && iteratedEvent.getTask().getStartTime() >= task.getStartTime() + task.getDuration()){
                        break;
                    } else {
                        previousEvent = iteratedEvent;
                        iteratedEvent = iteratedEvent.getNext();
                    }
                }
                if (previousEvent == null){ // Handles adding first task
                    schedule.addFirst(nodeToAdd);
                } else {
                    schedule.addAfter(previousEvent, nodeToAdd);
                }
                //nodeToAdd.setNext(iteratedEvent);

                previousEvent = nodeToAdd;

                //FIXME fix this atrocity
                int testfrequency=10000000;
                if (frequency == 7){
                    testfrequency = 1;
                  } else {
                    testfrequency = 7;
                  }

                iteratedDate = DateCalculator.addDaysToDate(iteratedDate, testfrequency);
            }

        } else {
            //FIXME: Can't add task. Maybe throw an error here. - Alex
            System.out.println("u stupid");
        }
    }

    private void addTaskInstancesToSchedule(Task task){

    }

    public void removeTask(Task task){
        String name = task.getName();
        ScheduleNode node = schedule.getHead();
        ScheduleNode prevNode = null;
        while (node != null){
            //System.out.println("Iterated through: " + node.getTask().getName());
            if (node.getTask().getName().equals(name)){
                if (prevNode != null){
                    prevNode.setNext(node.getNext());
                    node = node.getNext();
                } else {
                    schedule.removeFirst();
                }
            } else {
                prevNode = node;
                node = node.getNext();
            }
        }

        for (int i = 0; i < tasksGeneral.size(); i++){
            if (tasksGeneral.get(i).getName().equals(name)){
                tasksGeneral.remove(i);
                i--;
            }
        }
    }

    // update task by searching for it, and replacing it with the new updated information
    public void updateTask(String taskToUpdate, Task taskToReplace){
        // Finding the task by its name by searching through all the tasks we have already
        // Used to store the existing tasks info
        Task existingTask = findTaskByName(taskToUpdate);

        if (existingTask != null) {
            // Updates the existing task directly. (task to update) with the new task (task to replace) info
            // Moving all info from taskToReplace to taskToUpdate
            existingTask.setName(taskToReplace.getName());
            existingTask.setStartDate(taskToReplace.getStartDate());
            existingTask.setEndDate(taskToReplace.getEndDate());
            existingTask.setStartTime(taskToReplace.getStartTime());
            existingTask.setDuration(taskToReplace.getDuration());
            existingTask.setFrequency(taskToReplace.getFrequency());

            // check for overlaps with other tasks in the PSS
            if (!checkForOverlaps(existingTask)) {
               // Successful update of task
               System.out.println("Task " + taskToUpdate + " has been updated successfully!");
            } else {
                // handles overlaps between two tasks case.
                System.out.println("Cannot update task due to overlap with existing tasks.");
            }
        }
        else {
            // handles task not found case.
            System.out.println("The task " + taskToUpdate + " does not exist or cannot be found.");
        }
    }

    public Task findTaskByName(String name){
        for (Task task: tasksGeneral){
            if (task.getName().equals(name)){
                return task;
            }
        }
        return null;
    }


    //FIXME: This is a lengthy method and I would like it shorter -Alex
    // Returns true if there is an overlap
    public boolean checkForOverlaps(Task task){
        int frequency = task.getFrequency();
        int startDate = task.getStartDate();
        int endDate = task.getEndDate();

        //Check if occurs in the same day
        // If occurs in the same day, check duration of both
        int iteratedDate = startDate;
        ScheduleNode workingScheduleNode = schedule.getHead();
        while (iteratedDate <= endDate && workingScheduleNode != null){
            Task existingTask = workingScheduleNode.getTask();
            int existingTaskDateInstance = existingTask.getDateInstance();

            // If dates collide. Else, iterate next
            if (existingTaskDateInstance == iteratedDate){
                // Possible Conflict, Check Durations

                // if durations collide. Else, iterate next
                if (checkDurationOverlap(task, existingTask)){
                    System.out.println("There is an overlap cannot add task or update task at the specified date and time due to this.");
                    return true;
                }
            } else {
                // No conflict, Iterate next on the list, either next frequency or next existing event
            }

            if (existingTaskDateInstance > iteratedDate){
                //FIXME fix this atrocity too
                int testfrequency=10000000;
                if (frequency == 7){
                    testfrequency = 1;
                } else {
                    testfrequency = 7;
                }
                iteratedDate = DateCalculator.addDaysToDate(iteratedDate, testfrequency);
            } else {
                workingScheduleNode = workingScheduleNode.getNext();
            }
        }
        // There was no overlap. Task can be successfully added or updated!
        return false;
    }

    // false if no overlap
    private boolean checkDurationOverlap(Task taskA, Task taskB){
        float taskAStart = taskA.getStartTime();
        float taskBStart = taskB.getStartTime();
        float taskAEnd = taskAStart + taskA.getDuration();
        float taskBEnd = taskBStart + taskB.getDuration();

        if (taskAStart >= taskBStart && taskAStart <= taskBEnd){
            return true;
        } else if (taskAEnd >= taskBStart && taskAEnd <= taskBEnd){
            return true;
        }
        return false;
    }

    public List<Task> listAllTasks(){
        return tasksGeneral;
    }

    public void setSchedule(ScheduleLinkedList list){
        this.schedule = list;
    }

    public List<Task> getTimeFrame(int startDate, int endDate){
        List<Task> tasksInTimeFrame = new ArrayList<>();

        ScheduleNode head = schedule.getHead();
        while (head != null){
            Task workingTask = head.getTask();
            int dateInstance = workingTask.getDateInstance();
            if (dateInstance >= startDate || dateInstance <= endDate){
                tasksInTimeFrame.add(workingTask);
                head = head.getNext();
            }
        }
        return tasksInTimeFrame;
    }
}
