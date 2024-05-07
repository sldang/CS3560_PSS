import java.util.List;
import java.util.ArrayList;


public class ScheduleLinkedList {
    private ScheduleNode head;

    public ScheduleLinkedList() {
        this.head = null;
    }

    public void addFirst(ScheduleNode scheduleNode) {
        if (scheduleNode != null) {
            scheduleNode.setNext(null);
    
            scheduleNode.setNext(head);
            head = scheduleNode;
        }
    }
    

    public void addLast(ScheduleNode scheduleNode) {
        if (head == null) {
            head = scheduleNode;
        } else {
            ScheduleNode current = head;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(scheduleNode);
        }
    }

    public void addAfter(ScheduleNode before, ScheduleNode after) {
        if (before != null && after != null) {
            after.setNext(before.getNext());
            before.setNext(after);
        }
    }

    public void clear() {
        head = null;
    }

    public boolean contains(ScheduleNode scheduleNode) {
        ScheduleNode current = head;
        while (current != null) {
            if (current.equals(scheduleNode)) {
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    public void remove(ScheduleNode scheduleNode) {
        if (head == null || scheduleNode == null) {
            return;
        }

        if (head.equals(scheduleNode)) {
            head = head.getNext();
            return;
        }

        ScheduleNode current = head;
        while (current.getNext() != null) {
            if (current.getNext().equals(scheduleNode)) {
                current.setNext(current.getNext().getNext());
                return;
            }
            current = current.getNext();
        }
    }

    public void removeFirst() {
        if (head != null) {
            head = head.getNext();
        }
    }

    public void removeLast() {
        if (head == null) {
            return;
        }

        if (head.getNext() == null) {
            head = null;
            return;
        }

        ScheduleNode current = head;
        while (current.getNext().getNext() != null) {
            current = current.getNext();
        }
        current.setNext(null);
    }

    public ScheduleNode getScheduleNodeByName(String name) {
        ScheduleNode current = head;
        while (current != null) {
            if (current.getTask().getName().equals(name)) {
                return current;
            }
            current = current.getNext();
        }
        return null;
    }


    public ScheduleNode getHead(){
        return head;
    }
}

class ScheduleNode {
    private Task task;
    private ScheduleNode next;

    public ScheduleNode(Task task) {
        this.task = task;
        this.next = null;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public ScheduleNode getNext() {
        return next;
    }

    public void setNext(ScheduleNode next) {
        this.next = next;
    }
}

class ScheduleReplacementNode extends ScheduleNode {
    private List<Task> replacementTasks;

    public ScheduleReplacementNode(Task originalTask, List<Task> replacementTasks) {
        super(originalTask);
        this.replacementTasks = new ArrayList<>(replacementTasks);
    }

    public List<Task> getReplacementTasks() {
        return replacementTasks;
    }
}
