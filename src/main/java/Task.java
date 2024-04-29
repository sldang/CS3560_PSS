import java.util.ArrayList;
import java.util.List;

public interface Task {
   String getName();
   void setName(String name);

   String getType();
   void setType(String type);

   float getStartTime();
   void setStartTime(float startTime);

   float getDuration();
   void setDuration(float duration);

   int getStartDate();
   void setStartDate(int startDate);

   int getEndDate();
   void setEndDate(int endDate);

   List<Task> getLinkedTasks();
   void addLinkedTask(Task task);
}


class RecurringTask implements Task {
    private String name;
    private String type;
    private float startTime;
    private float duration;
    private int startDate;
    private int endDate;
    private List<Task> linkedTasks;

    // Contructor
    public RecurringTask() {
        linkedTasks = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public float getStartTime() {
        return startTime;
    }

    @Override
    public void setStartTime(float startTime) {
        this.startTime = startTime;
    }

    @Override
    public float getDuration() {
        return duration;
    }

    @Override
    public void setDuration(float duration) {
        this.duration = duration;
    }

    @Override
    public int getStartDate() {
        return startDate;
    }

    @Override
    public void setStartDate(int startDate) {
        this.startDate = startDate;
    }

    @Override
    public int getEndDate() {
       return endDate;
    }

    @Override
    public void setEndDate(int endDate) {
        this.endDate = endDate;
    }

    @Override
    public List<Task> getLinkedTasks() {
       return linkedTasks;
    }

    @Override
    public void addLinkedTask(Task task) {
        linkedTasks.add(task);
    }

}

class TransientTask implements Task {
    private String name;
    private String type;
    private float startTime;
    private float duration;
    private int startDate;

    // Contructor
    public TransientTask() {
    
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public float getStartTime() {
        return startTime;
    }

    @Override
    public void setStartTime(float startTime) {
        this.startTime = startTime;
    }

    @Override
    public float getDuration() {
        return duration;
    }

    @Override
    public void setDuration(float duration) {
        this.duration = duration;
    }

    @Override
    public int getStartDate() {
        return startDate;
    }

    @Override
    public void setStartDate(int startDate) {
        this.startDate = startDate;
    }

    @Override
    public int getEndDate() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getEndDate'");
    }

    @Override
    public void setEndDate(int endDate) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setEndDate'");
    }

    @Override
    public List<Task> getLinkedTasks() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getLinkedTasks'");
    }

    @Override
    public void addLinkedTask(Task task) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addLinkedTask'");
    }
    
}

class AntiTask implements Task {

}