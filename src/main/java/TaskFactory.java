public class TaskFactory {
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

