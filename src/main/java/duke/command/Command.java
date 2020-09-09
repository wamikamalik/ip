package duke.command;

import duke.exception.DukeException;
import duke.exception.ExceptionType;

public class Command {

    private String message;

    public Command(String message) {
        this.message = message;
    }

    public CommandType extractType() throws DukeException {
        message = message.trim();
        String command = message;
        if(message.contains(" ")) {
            command = message.split(" ")[0];
        }
        if(command.equalsIgnoreCase("bye")) {
            return CommandType.EXIT;
        } else if(command.equalsIgnoreCase("list")) {
            return CommandType.LIST;
        } else if(command.equalsIgnoreCase("done")) {
            return CommandType.MARK_DONE;
        } else if(message.length()<4) {
            throw new DukeException(ExceptionType.UNIDENTIFIED);
        }
        if(command.equalsIgnoreCase("todo")){
            return CommandType.TODO;
        } else if(command.equalsIgnoreCase("deadline")){
            return CommandType.DEADLINE;
        } else if(command.equalsIgnoreCase("event")) {
            return CommandType.EVENT;
        } else {
            throw new DukeException(ExceptionType.UNIDENTIFIED);
        }
    }

    public String getMessage() {
        return message;
    }
}
