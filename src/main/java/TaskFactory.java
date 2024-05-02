import java.util.HashMap;
import java.util.Map;

public class TaskFactory {
    private static Map<String, String> typeTranslation = new HashMap<>();
    static {
        String[] transientTypes = {"Appointment", "Movie", "Shopping"};
        String[] reccuringTypes = {"Class", "Excersize"};
        String[] antiTypes = {"Cancellation"};
        for (String type : reccuringTypes) {
            typeTranslation.put(type, "RecurringTask");
        }
        for (String type : transientTypes) {
            typeTranslation.put(type, "TransientTask");
        }
        for (String type : antiTypes) {
            typeTranslation.put(type, "AntiTask");
        }
    }

    public static String getTranslation(String type){
        if (typeTranslation.containsKey(type)){
            return typeTranslation.get(type);
        } else {
            //throw an error or something
        }
        return type;
    }
    private static TaskFactory instance;
    int date;

    // constructor
    private TaskFactory(){

    }

    // Gets a singleton instance of the TaskFactory
    public static TaskFactory getInstance() {
        if (instance == null) {
            instance = new TaskFactory();
        }
        return instance;
    }

    // To specify which task type to create
    public Task createTask(String type) {
        switch (type) {
            case "RecurringTask":
                return new RecurringTask();
            case "TransientTask":
                return new TransientTask();
            case "AntiTask":
                return new AntiTask();
            default:
                return null;
        }
    }
}



