package duke.task;

import duke.command.CommandType;
import duke.command.Parser;
import duke.exception.DukeException;
import duke.exception.ExceptionType;

import java.util.ArrayList;

public class TaskList extends ArrayList<Task>{

    public static final int DEADLINE_LEN = 9;
    public static final int EVENT_LEN = 6;
    public static final String DEADLINE_IDENTIFIER = "/by";
    public static final String EVENT_IDENTIFIER = "/on";
    public static final int DONE_TODO_LEN = 5;

    public TaskList() {
        super();
    }

    public TaskList(ArrayList<Task> tasks) {
        super.addAll(tasks);
    }

    /** Adds the new task to the list.
     *
     * @param parser the command parser for the given input.
     * @param command task name given by the user.
     * @param type the type of task.
     * @throws DukeException duke error.
     */
    public void add(Parser parser, String command, CommandType type) throws DukeException {

        switch (type) {
        case TODO:
            try {
                String todoName = command.substring(DONE_TODO_LEN).trim();
                super.add(new Todo(todoName));
            } catch (StringIndexOutOfBoundsException e) {
                throw new DukeException(ExceptionType.MISSING_DESCRIPTION);
            }
            break;
        case DEADLINE:
            String[] rawName = command.trim().split(DEADLINE_IDENTIFIER);
            parser.checkCommand(rawName, "deadline", DEADLINE_IDENTIFIER);
            String deadlineName = rawName[0].substring(DEADLINE_LEN).trim();
            String by = rawName[1].trim();
            String byDate = Parser.toDate(by);
            super.add(new Deadline(deadlineName, byDate));
            break;
        case EVENT:
            String[] rawEventName = command.trim().split(EVENT_IDENTIFIER);
            parser.checkCommand(rawEventName, "event", EVENT_IDENTIFIER);
            String eventName = rawEventName[0].substring(EVENT_LEN).trim();
            String on = rawEventName[1].trim();
            String onDate = Parser.toDate(on);
            super.add(new Event(eventName, onDate));
            break;
            default:
                throw new DukeException(ExceptionType.UNIDENTIFIED);
        }
    }

    /** Deletes the given item from the list.
     *
     * @param itemNo index of the item to be deleted.
     * @throws DukeException duke error.
     */
    public Task delete(int itemNo) throws DukeException {
        if(itemNo == 0) {
            throw new DukeException(ExceptionType.NOT_A_NUMBER);
        } else if(itemNo >Task.getNoOfTasks()) {
            throw new DukeException(ExceptionType.NOT_IN_LIST);
        } else {
            Task taskToDelete = super.get(itemNo -1);
            if(!taskToDelete.isDone()) {
                Task.setNoOfIncompleteTasks(Task.getNoOfIncompleteTasks() - 1);
            }
            Task.setNoOfTasks(Task.getNoOfTasks() - 1);
            super.remove(itemNo -1);
            return taskToDelete;
        }
    }

    /** Mark the given item as done.
     *
     * @param itemNo index of the item to be marked as done.
     * @throws DukeException duke error.
     */
    public Task done(int itemNo) throws DukeException {

        if(itemNo == 0) {
            throw new DukeException(ExceptionType.NOT_A_NUMBER);
        } else if(itemNo >Task.getNoOfTasks()) {
            throw new DukeException(ExceptionType.NOT_IN_LIST);
        } else if(super.get(itemNo -1).isDone()) {
            throw new DukeException(ExceptionType.ALREADY_DONE);
        } else {
            Task setDone = super.get(itemNo -1);
            setDone.markAsDone();
            return setDone;
        }
    }

}
