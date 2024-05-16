import java.util.List;
import java.util.ArrayList;


public class ScheduleLinkedList {
    private ScheduleNode head;
    private ScheduleNode tail;

    public ScheduleLinkedList() {
        this.head = null;
        this.tail = null;
    }

    public void addFirst(ScheduleNode scheduleNode) {
        if (scheduleNode != null) {
            scheduleNode.setNext(null);
    
            scheduleNode.setNext(head);
            head = scheduleNode;
            if (tail == null){
                tail = scheduleNode;
            }
        }
    }
    

    public void addLast(ScheduleNode scheduleNode) {
        if (head == null) {
            head = scheduleNode;
        } else {
            tail.setNext(scheduleNode);
        }
        tail = scheduleNode;
    }

    public void addAfter(ScheduleNode before, ScheduleNode after) {
        if (before != null && after != null) {
            after.setNext(before.getNext());
            before.setNext(after);
            if (tail.equals(before)){
                tail = after;
            }
        }
    }

    public void clear() {
        head = null;
        tail = null;
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
            if (tail.equals(scheduleNode)){
                tail = head; //= null
            }
            return;
        }

        ScheduleNode current = head;
        while (current.getNext() != null) {
            if (current.getNext().equals(scheduleNode)) {
                current.setNext(current.getNext().getNext());
                if (tail.equals(scheduleNode)){
                    tail = current;
                }
                return;
            }
            current = current.getNext();
        }
    }

    public void removeFirst() {
        if (head != null) {
            if (tail.equals(head)){
                tail = null;
            }
            head = head.getNext(); //=null if first
        }
    }

    public void removeLast() {
        if (head == null) {
            return;
        }

        if (head.getNext() == null) {
            head = null;
            tail = null;
            return;
        }

        ScheduleNode current = head;
        while (current.getNext().getNext() != null) {
            current = current.getNext();
        }
        tail = current;
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

    public ScheduleLinkedList getCopy(){
        ScheduleLinkedList copy = new ScheduleLinkedList();
        ScheduleNode node = head;
        while (node != null){
            copy.addLast(node);
            node = node.getNext();
        }
        return copy;
    }
}

class ScheduleNode {
    private Task task;
    private ScheduleNode next;
    private Task correspondingTask = null;

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

    public Task getCorrespondingTask() {
        return correspondingTask;
    }
}

class ScheduleReplacementNode extends ScheduleNode {
    private Task correspondingTask;

    public ScheduleReplacementNode(Task task, Task correspondingTask) {
        super(task);
        this.correspondingTask = correspondingTask;
    }

    public Task getCorrespondingTask() {
        return correspondingTask;
    }

    public void setCorrespondingTask(Task replacementTask) {
        this.correspondingTask = replacementTask;
    }
}
