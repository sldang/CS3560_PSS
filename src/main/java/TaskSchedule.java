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
        if (TaskFactory.getTranslation(task.getType()).equals("AntiTask")) {
            int removeDate = task.getStartDate();
            ScheduleNode iteratedEvent = schedule.getHead();
            ScheduleNode previousEvent = null;

            boolean match = false;

            //FIXME: fix this atrocity
            while (iteratedEvent != null){
                if (iteratedEvent.getTask().getDateInstance() == removeDate &&
                    iteratedEvent.getTask().getStartTime() == task.getStartTime() &&
                    iteratedEvent.getTask().getStartTime() + iteratedEvent.getTask().getDuration() == task.getStartTime() + task.getDuration() &&
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

    //Task must be a reference to an actual task, otherwise it will not match and will not find.
    public void removeTask(Task task){
        String name = task.getName();
        ScheduleNode node = schedule.getHead();
        ScheduleNode prevNode = null;

        String type = task.getType();

        //Scenarios
        //Removing an AntiTask and there is a task filling in it
        //Removing a Recurring task and there is an AntiTask

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
                // Found, check if another task uses the same time period.
                ScheduleNode nextNode = node.getNext();
                if (nextNode != null &&
                        nextNode.getTask().getDateInstance() == task.getDateInstance() &&
                        checkDurationOverlap(nextNode.getTask(), task)) {

                    // There is an overlap, cannot proceed.
                    System.err.println("Deletion of the AntiTask would cause a conflict!");
                    // Maybe print about the dates + durations of both tasks

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
            while (node != null){
                //System.out.println("Iterated through: " + node.getTask().getName());
                if (node.getTask().getName().equals(name)){
                    schedule.remove(node);
                } else if ((node.getCorrespondingTask() != null && node.getCorrespondingTask().getName().equals(name))){
                    schedule.remove(node);
                    tasksGeneral.remove(node.getTask());
                }
                node = node.getNext();
            }
            tasksGeneral.remove(task);
            System.out.println("Task removed!");
        }
    }

    // update task by searching for it, and replacing it with the new updated information
    public void updateTask(String taskToUpdate, Task taskToReplace){

        // First, find the existing task by its name
        Task existingTask = findTaskByName(taskToUpdate);

        if (existingTask == null) {
            // Handle task not found case
            System.out.println("The task '" + taskToUpdate + "' does not exist or cannot be found.");
            return;
        }

        // Next, check for overlaps with other tasks
        if (!checkForOverlaps(taskToReplace)) {
            // If no overlaps, proceed to update the task
            existingTask.setName(taskToReplace.getName());
            existingTask.setStartDate(taskToReplace.getStartDate());
            existingTask.setEndDate(taskToReplace.getEndDate());
            existingTask.setStartTime(taskToReplace.getStartTime());
            existingTask.setDuration(taskToReplace.getDuration());
            existingTask.setFrequency(taskToReplace.getFrequency());

            System.out.println("Task '" + taskToUpdate + "' has been updated successfully!");
        } else {
            // Handle overlaps between tasks
            System.out.println("Cannot update task due to overlap with existing tasks.");
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
                    if (!TaskFactory.getTranslation(existingTask.getType()).equals("AntiTask")){
                        System.out.println("There is an overlap cannot add task or update task at the specified date and time due to this.");
                        return true;
                    }
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

        if (taskAStart >= taskBStart && taskAStart < taskBEnd){
            return true;
        } else if (taskAEnd > taskBStart && taskAEnd <= taskBEnd){
            return true;
        } else if (taskAStart < taskBStart && taskAEnd > taskBEnd){
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
            if (dateInstance >= startDate && dateInstance <= endDate){  // Corrected logical AND
                tasksInTimeFrame.add(workingTask);
            }
            head = head.getNext();
        }
        return tasksInTimeFrame;
    }    
}
