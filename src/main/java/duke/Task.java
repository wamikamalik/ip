package duke;

public abstract class Task {

    /**Description of task*/
    private String name;
    /**records the completion of the task*/
    private boolean isDone;
    /**total number of tasks in the list*/
    private static int noOfTasks = 0;
    /**number of incomplete tasks*/
    public static int noOfIncompleteTasks = 0;

    public Task(String name) {
        this.name = name;
        this.isDone = false;
        noOfTasks += 1;
        noOfIncompleteTasks+=1;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return (isDone ? "\u2713" : "\u2718");
    }

    public void markAsDone() {
        this.isDone = true;
        noOfIncompleteTasks-=1;
    }

    public static int getNoOfTasks() {
        return noOfTasks;
    }

    public static int getNoOfIncompleteTasks() {
        return noOfIncompleteTasks;
    }

    public boolean isDone() {
        return isDone;
    }

    @Override
    public String toString() {
        return "[" + getIcon() + "] " + getName();
    }
}
