import org.json.JSONObject;

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

   JSONObject toJSONObject();
   void fromJSONObject(JSONObject jsonObject);
}


class RecurringTask implements Task {
    private String name;
    private String type;
    private float startTime;
    private float duration;
    private int startDate;
    private int endDate;
    private int frequency;
    private List<Task> linkedTasks;

    // Contructor
    public RecurringTask() {
        linkedTasks = new ArrayList<>();
    }

    // Constructor from JSONObject
    public void fromJSONObject(JSONObject jsonObject){
        name = jsonObject.getString("Name");
        type = jsonObject.getString("Type");
        startDate = jsonObject.getInt("StartDate");
        startTime = jsonObject.getFloat("StartTime");
        duration = jsonObject.getFloat("Duration");
        endDate = jsonObject.getInt("EndDate");
        frequency = jsonObject.getInt("Frequency");
    }

    public JSONObject toJSONObject(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Name", this.getName());
        jsonObject.put("Type", this.getType());
        jsonObject.put("StartDate", this.getStartDate());
        jsonObject.put("StartTime", this.getStartTime());
        jsonObject.put("Duration", this.getDuration());
        jsonObject.put("EndDate", this.getEndDate());
        jsonObject.put("Frequency", this.getFrequency());

        // Add more fields if necessary, especially handling optional fields and linked tasks
        return jsonObject;
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

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}

class TransientTask implements Task {
    private String name;
    private String type;
    private float startTime;
    private float duration;
    private int date;

    // Contructor
    public TransientTask() {
    
    }

    // Constructor from JSONObject
    public void fromJSONObject(JSONObject jsonObject){
        name = jsonObject.getString("Name");
        type = jsonObject.getString("Type");
        date = jsonObject.getInt("Date");
        startTime = jsonObject.getFloat("StartTime");
        duration = jsonObject.getFloat("Duration");
    }

    public JSONObject toJSONObject(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Name", this.getName());
        jsonObject.put("Type", this.getType());
        jsonObject.put("Date", this.getStartDate());
        jsonObject.put("StartTime", this.getStartTime());
        jsonObject.put("Duration", this.getDuration());

        // Add more fields if necessary, especially handling optional fields and linked tasks
        return jsonObject;
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
        return date;
    }

    @Override
    public void setStartDate(int startDate) {
        this.date = startDate;
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
    private String name;
    private String type;
    private float startTime;
    private float duration;
    private int startDate;
    private List<Task> linkedTasks;

    // Contructor
    public AntiTask() {
    
    }

    public void fromJSONObject(JSONObject jsonObject){
        name = jsonObject.getString("Name");
        type = jsonObject.getString("Type");
        startDate = jsonObject.getInt("StartDate");
        startTime = jsonObject.getFloat("StartTime");
        duration = jsonObject.getFloat("Duration");
    }

    public JSONObject toJSONObject(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Name", this.getName());
        jsonObject.put("Type", this.getType());
        jsonObject.put("Date", this.getStartDate());
        jsonObject.put("StartTime", this.getStartTime());
        jsonObject.put("Duration", this.getDuration());

        // Add more fields if necessary, especially handling optional fields and linked tasks
        return jsonObject;
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
       return linkedTasks;
    }

    @Override
    public void addLinkedTask(Task task) {
        linkedTasks.add(task);
    }
}