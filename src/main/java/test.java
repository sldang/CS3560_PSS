public class test {

    public static void main(String[] args){
        System.out.println("This works");
        System.out.println("");

        TaskFactory factory = TaskFactory.getInstance();
        // Testing the creation of a new TransientTask
        System.out.println("TransientTask example: ");
        Task transientTask = factory.createTask("TransientTask");

        // adding info for the transient task
        transientTask.setName("CS3560 Presentation");
        System.out.println(transientTask.getName());
        transientTask.setType("Appointment/Meeting");
        System.out.println(transientTask.getType());
        transientTask.setStartTime(4.15f);
        System.out.println(transientTask.getStartTime());
        transientTask.setDuration(15);
        System.out.println(transientTask.getDuration());
        transientTask.setStartDate(20240507);
        System.out.println(transientTask.getStartDate());
    }

    public static int addition(int x, int y) {
        return x + y;
    }
}
