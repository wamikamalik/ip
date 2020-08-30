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
        } else if(message.length()<5) {
            return CommandType.NONE;
        } else {
            if(command.equalsIgnoreCase("todo")){
                return CommandType.TODO;
            } else if(command.equalsIgnoreCase("deadline")){
                return CommandType.DEADLINE;
            } else if(command.equalsIgnoreCase("event")) {
                return CommandType.EVENT;
            } else {
                return CommandType.NONE;
            }
        }
    }

    public String getMessage() {
        return message;
    }
}
