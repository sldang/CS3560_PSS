import java.util.ArrayList;
import java.util.List;

public class TaskSchedule {

    private ScheduleLinkedList schedule;
    private static TaskSchedule instance;
    private int currentYear = 0;

    private TaskSchedule(){

    }

    public static TaskSchedule getInstance(){
        if (instance == null){
            instance = new TaskSchedule();
        }
        return instance;
    }

    public void addTask(Task task){

    }

    public void removeTask(Task task){

    }

    //@FIXME There should be a better way to handle this. Should we search by name? How do we edit? -Alex
    public void updateTask(Task taskToUpdate, Task taskToReplace){

    }

    public void findTaskByName(String name){

    }

    public boolean checkForOverlaps(Task task){
        return false;
    }

    //FIXME: This might be changed with implementation of ScheduleLinkedList.java, or however we want to view it.
    public List<Task> listAllTasks(){
        return null;
    }

    //FIXME: This might be changed with implementation of ScheduleLinkedList.java, or however we want to save it.
    public void setSchedule(ScheduleLinkedList list){

    }

    public List<Task> getTimeFrame(int startDate, int endDate){
        List<Task> tasksInTimeFrame = new ArrayList<>();
        // FIXME: implementation
        return null;
    }

    public int getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(int currentYear) {
        this.currentYear = currentYear;
    }
}
