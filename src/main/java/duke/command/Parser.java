package duke.command;

import duke.exception.DukeException;
import duke.exception.ExceptionType;

public class Parser {

    public static CommandType extractType(String message) throws DukeException {
        //message = message.trim();
        String command = message.trim();
        if(command.contains(" ")) {
            command = command.split(" ")[0];
        }
        if(command.equalsIgnoreCase("bye")) {
            return CommandType.EXIT;
        } else if(command.equalsIgnoreCase("list")) {
            return CommandType.LIST;
        } else if(command.equalsIgnoreCase("done")) {
            return CommandType.MARK_DONE;
        } else if(command.equalsIgnoreCase("delete")) {
            return CommandType.DELETE;
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

    /** checks the event or deadline command to ensure the input is right and complete.
     *
     * @param command task name given by the user.
     * @param rawName array of description and deadline/date.
     * @param taskName type of task.
     * @param taskIdentifier on or by task.
     * @throws DukeException duke error.
     */
    public static void checkCommand(String command, String[] rawName, String taskName, String taskIdentifier) throws DukeException {
        if (rawName[0].trim().equalsIgnoreCase(taskName)) {
            throw new DukeException(ExceptionType.MISSING_DESCRIPTION);
        }
        if (!command.contains(taskIdentifier)) {
            throw new DukeException(ExceptionType.MISSING_IDENTIFIER);
        }
        if (rawName.length < 2) {
            throw new DukeException(ExceptionType.MISSING_ON_BY);
        }
    }

    /** Get the item number from the command entered.
     *
     * @param command the done command given.
     * @return the item number.
     */
    public static int extractItemNo(String command, int commandLen) {

        int itemNo;
        try {
            String rawItemNo = command.trim().substring(commandLen, command.length()).trim();
            if(rawItemNo.endsWith(".")) {
                rawItemNo = rawItemNo.substring(0,rawItemNo.length()-1);
            }
            itemNo = Integer.parseInt(rawItemNo);
        } catch (NumberFormatException | StringIndexOutOfBoundsException error) {
            itemNo = 0;
        }
        return itemNo;

    }

}
