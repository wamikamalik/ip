package duke;

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
