public class Task {

    private String name;
    private static int noOfTasks = 0;

    public Task() {
        this("");
    }

    public Task(String name) {
        this.name = name;
        noOfTasks += 1;
    }

    public String getName() {
        return name;
    }

    public static int getNoOfTasks() {
        return noOfTasks;
    }
}
