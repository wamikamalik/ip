public enum ExceptionType {
    MISSING_DESCRIPTION("\n\tOh no! The Task description is missing :(\n"),
    MISSING_IDENTIFIER("\n\tOofies! You didn't mention the time (/on) or deadline (/by).\n"),
    UNIDENTIFIED("\n\tSorry :( I don't understand what you just said.\n");

    private String message;

    ExceptionType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
