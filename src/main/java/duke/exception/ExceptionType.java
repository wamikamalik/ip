package duke.exception;

public enum ExceptionType {
    MISSING_DESCRIPTION("\n\tOh no! The Task description is missing :(\n"),
    MISSING_IDENTIFIER("\n\tOofies! You didn't mention the time (/on) or deadline (/by).\n"),
    UNIDENTIFIED("\n\tSorry :( I don't understand what you just said.\n"),
    NOT_A_NUMBER("\n\tBump, that's not a valid item number!\n"),
    MISSING_ON_BY("\n\tOh dear! You forgot the date/time/day.\n");

    private String message;

    ExceptionType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
