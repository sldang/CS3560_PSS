import java.util.List;

public class TaskSchedule {

    ScheduleLinkedList schedule;
    TaskSchedule instance;

    private TaskSchedule(){

    }

    public TaskSchedule getInstance(){
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
}
