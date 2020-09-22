package duke.command;

import duke.exception.DukeException;
import duke.exception.ExceptionType;

public class Parser {

    private String message;

    public Parser(String message) {
        this.message = message;
    }

    public CommandType extractType() throws DukeException {
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
        } else if(command.equalsIgnoreCase("find")) {
            return CommandType.FIND;
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
     * @param rawName array of description and deadline/date.
     * @param taskName type of task.
     * @param taskIdentifier on or by task.
     * @throws DukeException duke error.
     */
    public void checkCommand(String[] rawName, String taskName, String taskIdentifier) throws DukeException {
        if (rawName[0].trim().equalsIgnoreCase(taskName)) {
            throw new DukeException(ExceptionType.MISSING_DESCRIPTION);
        }
        if (!message.contains(taskIdentifier)) {
            throw new DukeException(ExceptionType.MISSING_IDENTIFIER);
        }
        if (rawName.length < 2) {
            throw new DukeException(ExceptionType.MISSING_ON_BY);
        }
    }

    /** Get the item number from the command entered.
     *
     * @param commandLen the length of command given.
     * @return the item number.
     */
    public int extractItemNo(int commandLen) {

        int itemNo;
        try {
            String rawItemNo = message.trim().substring(commandLen, message.length()).trim();
            if(rawItemNo.endsWith(".")) {
                rawItemNo = rawItemNo.substring(0,rawItemNo.length()-1);
            }
            itemNo = Integer.parseInt(rawItemNo);
        } catch (NumberFormatException | StringIndexOutOfBoundsException error) {
            itemNo = 0;
        }
        return itemNo;

    }

    public String extractKeyword() throws DukeException {
        String keyword;
        String[] words = message.trim().split(" ");
        if(words.length>1) {
            keyword = words[1];
        } else {
            throw new DukeException(ExceptionType.MISSING_KEYWORD);
        }
        return keyword;
    }
}
