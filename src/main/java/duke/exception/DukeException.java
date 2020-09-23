package duke.exception;

/**
 * Represents the exceptions that can be thrown by Duke.
 */
public class DukeException extends Exception {

    private ExceptionType exception;

    public DukeException(ExceptionType exception) {
        this.exception = exception;
    }

    @Override
    public String toString() {
        return (exception.getMessage());
    }
}
