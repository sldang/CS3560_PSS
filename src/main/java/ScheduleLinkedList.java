import java.util.ArrayList;
import java.util.List;

public class ScheduleLinkedList {

    private ScheduleNode head;

    public ScheduleLinkedList(){

    }

    public void addFirst(ScheduleNode scheduleNode){

    }

    public void addLast(ScheduleNode scheduleNode){

    }

    public void addAfter(ScheduleNode before, ScheduleNode after) {

    }

    public void clear(){

    }

    public boolean contains(ScheduleNode scheduleNode) {
        return false;
    }

    public void remove(ScheduleNode scheduleNode){

    }

    public void removeFirst(){

    }

    public void removeLast(){

    }

    public ScheduleNode getScheduleNodeByName(String name) {
        return null;
    }

    public ScheduleNode getHead(){
        return null;
    }
}

class ScheduleNode {
    private Task task;
    private ScheduleNode next;

    public ScheduleLinkedList getLinkedList(){
        return null;
    }

    public ScheduleNode getNext(){
        return null;
    }

    public void setNext(ScheduleNode next){

    }

    public Task getTask(){
        return null;
    }

    public void setTask(Task task){

    }

}

class ScheduleReplacementNode extends ScheduleNode {
    private Task original;
    private Task antitask;
    private List<Task> replacementTasks;
    private ScheduleNode next;

    public ScheduleLinkedList getLinkedList(){
        return null;
    }

    public ScheduleNode getNext(){
        return null;
    }

    public void setNext(ScheduleNode next){

    }

    public Task getTask(){
        return null;
    }

    public void setTask(Task task){

    }
}
