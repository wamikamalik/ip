public class Task {

    private String name;
    private boolean isDone;
    private static int noOfTasks = 0;
    public static int incompleteTasks = 0;

    public Task() {
        this("");
    }

    public Task(String name) {
        this.name = name;
        this.isDone = false;
        noOfTasks += 1;
        incompleteTasks+=1;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return (isDone ? "\u2713" : "\u2718");
    }

    public void markAsDone() {
        this.isDone = true;
        incompleteTasks-=1;
    }

    public static int getNoOfTasks() {
        return noOfTasks;
    }

    public static int getIncompleteTasks() {
        return incompleteTasks;
    }

    public boolean isDone() {
        return isDone;
    }
}
