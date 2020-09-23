package duke.task;

/**
 * Represents a task that is an event.
 */
public class Event extends Task {
    protected String time;

    public Event(String description, String time) {
        super(description);
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + "(on: " + time + ")";
    }
}
