import static org.junit.jupiter.api.DynamicTest.stream;

import java.util.ArrayList;
import java.util.List;

public class TaskSchedule {

    private ScheduleLinkedList schedule = new ScheduleLinkedList(); // This is a calendar map that represents each event in time
    private List<Task> tasksGeneral = new ArrayList<>(); // This is a generalized list of all events (Recurrings are only listed once)
    private ScheduleLinkedList scheduleSave = new ScheduleLinkedList();
    private List<Task> tasksGeneralSave = new ArrayList<>();

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
    public boolean addTask(Task task){
        if (TaskFactory.getTranslation(task.getType()).equals("AntiTask")) {
            ScheduleNode iteratedEvent = schedule.getHead();
            ScheduleNode previousEvent = null;

            boolean match = false;

            // This will search through the ScheduleLinkedList for the particular instance
            while (iteratedEvent != null){
                // if (iteratedEvent is exactly the same time as task)
                if (exactDateAndTime(iteratedEvent.getTask(), task) &&
                    TaskFactory.getTranslation(iteratedEvent.getTask().getType()).equals("RecurringTask"))
                {
                    match = true;
                    // The anti task is valid
                    ScheduleNode replacementNode = new ScheduleReplacementNode(task, iteratedEvent.getTask());
                    replacementNode.setNext(iteratedEvent.getNext());

                    if (previousEvent != null){
                        previousEvent.setNext(replacementNode);
                    } else {
                        // Handles HEAD
                        schedule.addFirst(replacementNode);
                    }
                    tasksGeneral.add(task);
                    break;
                } else {
                    previousEvent = iteratedEvent;
                    iteratedEvent = iteratedEvent.getNext();
                }
            }

            if (!match){
                System.out.println("Antitask FAILED to find another task");
                return false;
            } else {
                return true;
            }
        } else if (!checkForOverlaps(task)){
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


                // This is a technique to find where to place the instance in the ScheduleLinkedList.
                // It finds the node that is supposed to be "right after" the node to be inserted.
                while (iteratedEvent != null && iteratedEvent.getTask().getDateInstance() <= iteratedDate){
                    // if (iteratedEvent has same Date and it's start time is = or after task's endTime)
                    if (iteratedEvent.getTask().getDateInstance() == iteratedDate &&
                        iteratedEvent.getTask().getStartTime() >= task.getStartTime() + task.getDuration()){
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

                previousEvent = nodeToAdd;

                // This should change the iteratedDate to the frequency
                // For this project, 1 = Daily, 7 = Weekly
                iteratedDate = DateCalculator.addDaysToDate(iteratedDate, frequency);
            }
            return true;

        } else {
            //FIXME: Can't add task. Maybe throw an error here. - Alex
            System.out.println("Try Again.");
            return false;
        }
    }

    // Returns True if both tasks occupy the same exact block
    private boolean exactDateAndTime(Task taskA, Task taskB){
        int taskADate = taskA.getDateInstance();
        int taskBDate = taskB.getDateInstance();
        float taskAStartTime = taskA.getStartTime();
        float taskBStartTime = taskB.getStartTime();
        float taskADuration = taskA.getDuration();
        float taskBDuration = taskB.getDuration();

        return taskADate == taskBDate && taskAStartTime == taskBStartTime && taskADuration == taskBDuration;

    }

    //Task must be a reference to an actual task, otherwise it will not match and will not find.
    public void removeTask(Task task){
        String name = task.getName();
        ScheduleNode node = schedule.getHead();
        ScheduleNode prevNode = null;

        //Scenarios
        //Removing an AntiTask and there is a task filling in it
        //Removing a Recurring task and there is an AntiTask

        // Removing an AntiTask
        if (TaskFactory.getTranslation(task.getType()).equals("AntiTask")){
            Task correspondingRecurring = null;
            while (node != null){
                if (node.getTask().equals(task)){
                    correspondingRecurring = node.getCorrespondingTask();
                    break;
                } else {
                    prevNode = node;
                    node = node.getNext();
                }
            }
            if (correspondingRecurring != null){
                // Found, check if  adjacent tasks would cause a conflict.
                ScheduleNode nextNode = node.getNext();
                if (nextNode != null &&
                        nextNode.getTask().getDateInstance() == task.getDateInstance() &&
                        checkDurationOverlap(nextNode.getTask(), task)) {

                    // There is an overlap, cannot proceed.
                    System.err.println("Deletion of the AntiTask would cause a conflict!");
                    // Maybe print about the dates + durations of both tasks

                } else if (prevNode != null &&
                        prevNode.getTask().getDateInstance() == task.getDateInstance() &&
                        checkDurationOverlap(prevNode.getTask(), task)){

                    System.err.println("Deletion of the AntiTask would cause a conflict!");

                } else {
                    // Perform a swap
                    ScheduleNode newNode = new ScheduleNode(correspondingRecurring);
                    schedule.remove(node);
                    schedule.addAfter(prevNode, newNode);

                    tasksGeneral.remove(task);
                    System.out.println("Task removed!");
                }
            } else {
                // Not found
                System.err.println("Task was not found!");
            }
        } else {
            // Removing a Recurring or Transient Task
            while (node != null){
                //System.out.println("Iterated through: " + node.getTask().getName());
                if (node.getTask().getName().equals(name)){
                    schedule.remove(node);
                // If current node is an AntiTask, then check if it's corresponding Recurring matches the name
                } else if ((node.getCorrespondingTask() != null && node.getCorrespondingTask().getName().equals(name))){
                    schedule.remove(node);
                    tasksGeneral.remove(node.getTask()); // remove AntiTask
                }
                node = node.getNext();
            }
            tasksGeneral.remove(task);
            System.out.println("Task removed!");
        }
    }

    // update task by searching for it, and replacing it with the new updated information
    public boolean updateTask(String taskToUpdate, Task taskToReplace){

        this.saveInstance();

        // First, find the existing task by its name
        Task existingTask = findTaskByName(taskToUpdate);

        if (existingTask == null) {
            // Handle task not found case
            System.out.println("The task '" + taskToUpdate + "' does not exist or cannot be found.");
            return false;
        }

        this.removeTask(existingTask);

        // Attempt to add task
        if (addTask(taskToReplace)) {
            System.out.println("Task '" + taskToUpdate + "' has been updated successfully!");
            return true;
        } else {
            // Handle overlaps between tasks
            System.out.println("Cannot update task due to overlap with existing tasks.");
            this.loadInstance();
            return false;
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
            boolean midnightLeak = false;

            // Check for midnight leak here
            // If they have the same date and no midnight
            // If existingTask leaks over midnight and the iteratedDate happens on the next day (existingTask + 1)
            // If iteratedDate has a leak over midnight and existingTask happens on the next day (existingTask - 1)
            // If they have the same date and they both leak (existing + 1, iterated + 1);
            // if ExistingTask leaks over midnight and IteratedDate happens on the same day (==)
            // if iteratedDate leaks over midnight and Existing Date happens on the same day (==)

            //Final
            // If existingDate has a midnight leak, check existingDate and existingDate+1
            // If iteratedDate has a midnight leak, check iteratedDate and iteratedDate+1

            int offset = 0;
            if (existingTask.getStartTime() + existingTask.getDuration() > 24){
                offset -= 1;
                midnightLeak = true;
            }
            if (task.getStartTime() + task.getDuration() > 24){
                offset += 1;
                midnightLeak = true;
            }

            // If dates collide. Else, iterate next
            if (existingTaskDateInstance == iteratedDate || (midnightLeak && existingTaskDateInstance == iteratedDate + offset)){
                // Possible Conflict, Check Durations

                // if durations collide. Else, iterate next
                if (checkDurationOverlap(task, existingTask)){
                    if (!TaskFactory.getTranslation(existingTask.getType()).equals("AntiTask")){
                        System.out.println("There is an overlap cannot add task or update task at the specified date and time due to this.");
                        return true;
                    }
                }
            } else {
                // No conflict, Iterate next on the list, either next frequency or next existing event
            }

            if (existingTaskDateInstance > iteratedDate){
                // This should change the iteratedDate to the frequency
                // For this project, 1 = Daily, 7 = Weekly
                iteratedDate = DateCalculator.addDaysToDate(iteratedDate, frequency);
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
        for (int i = -1; i <= 1; i++){

            // Simple way to test for durationOverlaps with midnight leaks
            float taskAAdjustedStart = taskAStart + i * 24;
            float taskAAdjustedEnd = taskAEnd + i * 24;


            // Bad:             [==B=[]=A==]
            // Bad:             [==A=[]=B==]
            // Bad:             [==[B]A==]
            // Bad (Implicit):  [==[A]B==]
            // Good:            [==A==][==B==]
            if (taskAAdjustedStart >= taskBStart && taskAAdjustedStart < taskBEnd){
                return true;
            } else if (taskAAdjustedEnd > taskBStart && taskAAdjustedEnd <= taskBEnd){
                return true;
            } else if (taskAAdjustedStart < taskBStart && taskAAdjustedEnd > taskBEnd){
                return true;
            }
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
            if (dateInstance >= startDate && dateInstance <= endDate){  // Corrected logical AND
                if (!TaskFactory.getTranslation(workingTask.getType()).equals("AntiTask")){
                    tasksInTimeFrame.add(workingTask);
                }
            }
            head = head.getNext();
        }
        return tasksInTimeFrame;
    }

    public void saveInstance(){
        scheduleSave = schedule.getCopy();
        tasksGeneralSave = new ArrayList<>(tasksGeneralSave);
    }

    public void loadInstance(){
        schedule = scheduleSave.getCopy();
        tasksGeneral = tasksGeneralSave;
    }
}
