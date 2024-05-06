import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TaskSchedule {

    private ScheduleLinkedList schedule; // This is a calendar map that represents each event in time
    private List<Task> tasksGeneral; // This is a generalized list of all events (Recurrings are only listed once)


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
            ScheduleNode previousEvent = schedule.getHead();
            while (iteratedDate <= task.getEndDate()){
                Task copiedTaskEvent = task.getCopyOf();
                copiedTaskEvent.setDateInstance(iteratedDate);
                ScheduleNode nodeToAdd = new ScheduleNode(copiedTaskEvent);
                nodeToAdd.setTask(copiedTaskEvent);


                if (previousEvent == null){ // Handles adding first task
                    schedule.addFirst(nodeToAdd);
                } else {
                    while (iteratedEvent != null && iteratedEvent.getTask().getDateInstance() <= iteratedDate){
                        if (iteratedEvent.getTask().getStartTime() >= task.getStartTime() + task.getDuration()){
                            break;
                        } else {
                            previousEvent = iteratedEvent;
                            iteratedEvent = iteratedEvent.getNext();
                        }
                    }
                    schedule.addAfter(previousEvent, nodeToAdd);
                    nodeToAdd.setNext(iteratedEvent);
                }
                previousEvent = nodeToAdd;

                iteratedDate = DateCalculator.addDaysToDate(iteratedDate, frequency);
            }

        } else {
            //FIXME: Can't add task. Maybe throw an error here. - Alex
        }
    }

    private void addTaskInstancesToSchedule(Task task){

    }

    public void removeTask(Task task){
        String name = task.getName();
        ScheduleNode node = schedule.getHead();
        while (node != null){
            if (node.getNext() != null && node.getTask().getName().equals(name)){
                node.setNext(node.getNext().getNext());
            }
        }
    }

    //@FIXME There should be a better way to handle this. Should we search by name? How do we edit? -Alex
    public void updateTask(Task taskToUpdate, Task taskToReplace){

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
                    return true;
                }
            } else {
                // No conflict, Iterate next on the list, either next frequency or next existing event
            }

            if (existingTaskDateInstance > iteratedDate){
                iteratedDate = DateCalculator.addDaysToDate(iteratedDate, frequency);
            } else {
                workingScheduleNode = workingScheduleNode.getNext();
            }
        }
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
            }
        }
        return tasksInTimeFrame;
    }
}
