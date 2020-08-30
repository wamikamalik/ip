public class Command {

    private String message;

    Command(String message) {
        this.message = message;
    }

    public CommandType extractType() {
        String command = message.trim().split(" ")[0];
        if(command.equalsIgnoreCase("bye")) {
            return CommandType.EXIT;
        } else if(command.equalsIgnoreCase("list")) {
            return CommandType.LIST;
        } else if(command.equalsIgnoreCase("done")) {
            return CommandType.MARK_DONE;
        } else {
            return CommandType.ADD;
        }
    }

    public String getMessage() {
        return message;
    }
}
