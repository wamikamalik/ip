package duke.exception;

public enum ExceptionType {
    MISSING_DESCRIPTION("\n\tOh no! The Task description is missing :(\n"),
    MISSING_IDENTIFIER("\n\tOofies! You didn't mention the time (/on) or deadline (/by).\n"),
    UNIDENTIFIED("\n\tSorry :( I don't understand what you just said.\n"),
    NOT_A_NUMBER("\n\tBump, please enter a valid item number!\n"),
    MISSING_ON_BY("\n\tOh dear! You forgot the date/time/day.\n"),
    NOT_IN_LIST("\n\tSuch an item does not exist.\n"),
    ALREADY_DONE("\n\tThis task was marked as done earlier!\n\tTo see the list of incomplete tasks simply type \"list\".\n"),
    WRONG_DATE_FORMAT("\n\tThe date should be in the format: dd-mm-yyyy HH:mm OR dd-mm-yyyy\n"),
    INVALID_DATE("\n\tThis date has passed already!\n");

    private String message;

    ExceptionType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
