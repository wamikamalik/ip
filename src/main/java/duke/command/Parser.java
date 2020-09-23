package duke.command;

import duke.exception.DukeException;
import duke.exception.ExceptionType;
import duke.task.Task;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents the message input by the user. It makes sense of the message by parsing it.
 */
public class Parser {

    private String message;

    public Parser(String message) {
        this.message = message;
    }

    /**
     * Extracts the job to be done from the raw message command.
     *
     * @return The type of command that was given by the user.
     * @throws DukeException If command input is unidentified.
     */
    public CommandType extractType() throws DukeException {
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
        } else if(command.equalsIgnoreCase("help")) {
            return CommandType.HELP;
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

    /**
     * Checks the event or deadline command to ensure the input is right and complete.
     *
     * @param rawName array of description and deadline/date.
     * @param taskName type of task,i.e, event or deadline.
     * @param taskIdentifier on or by task.
     * @throws DukeException If task description, on/by or date description is missing.
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

    /**
     * Get the item number of the item to be marked done or deleted from the command entered.
     *
     * @param commandLen the length of command given.
     * @return The item number.
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

    public static String toDate(String date) throws DukeException {
        String finalDate;
        try {
            if(date.trim().contains(" ")) {
                LocalDateTime d;
                LocalDateTime today = LocalDateTime.now();
                String[] parts = date.trim().split(" ");
                String[] dates = parts[0].split("-");
                String[] time = parts[1].split(":");
                d = LocalDateTime.of(Integer.parseInt(dates[2]), Integer.parseInt(dates[1]),
                        Integer.parseInt(dates[0]), Integer.parseInt(time[0]), Integer.parseInt(time[1]));
                if(d.isBefore(today)) {
                    throw new DukeException(ExceptionType.INVALID_DATE);
                }
                finalDate = d.format(DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm"));
            }
            else {
                LocalDate d;
                LocalDate today = LocalDate.now();
                String[] dates = date.trim().split("-");
                d = LocalDate.of(Integer.parseInt(dates[2]), Integer.parseInt(dates[1]),
                        Integer.parseInt(dates[0]));
                if(d.isBefore(today)) {
                    throw new DukeException(ExceptionType.INVALID_DATE);
                }
                finalDate = d.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
            }
        } catch (DateTimeException | ArrayIndexOutOfBoundsException | NumberFormatException e) {
            throw new DukeException(ExceptionType.WRONG_DATE_FORMAT);
        }
        return (finalDate);
    }

    public static boolean isToday(Task task) {
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
        if(task.toString().contains(today)) {
            return true;
        }
        return false;
    }
}
