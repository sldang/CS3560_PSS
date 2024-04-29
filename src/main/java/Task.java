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

}

class TransientTask implements Task {

}

class AntiTask implements Task {

}