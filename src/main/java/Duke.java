import duke.Storage.FileManager;
import duke.task.Task;
import duke.task.Todo;
import duke.task.Deadline;
import duke.task.Event;
import duke.command.Command;
import duke.command.CommandType;
import duke.exception.DukeException;
import duke.exception.ExceptionType;

import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.IOException;
import  java.util.Scanner;

public class Duke {

    public static final String BYE_MESSAGE = "\n\tSee you! Have a nice day!\n";
    public static final String DEADLINE_IDENTIFIER = "/by";
    public static final String EVENT_IDENTIFIER = "/on";
    public static final int DONE_TODO_LEN = 5;
    public static final int DELETE_LEN = 7;
    public static final int DEADLINE_LEN = 9;
    public static final int EVENT_LEN = 6;
    private static ArrayList<Task> tasks = new ArrayList<>();
    private static final String FILE = "duke.txt";
    private static final String DIR = "data";

    public static void printLine() {

        for(int i = 0; i<100; i++) {
            System.out.print("-");
        }
        System.out.println();

    }

    public static void main(String[] args) {

        printWelcomeMessage();
        FileManager file = new FileManager(FILE, DIR);
        Scanner in = new Scanner(System.in);
        Command command = new Command(in.nextLine());
        int itemNo;
        try {
            file.loadData(tasks);
        } catch (FileNotFoundException e) {
            System.out.println("\t\nThe World has gone crazy!");
            e.printStackTrace();
        }

        while(true) {
            //the following code interprets the command entered by the user and takes appropriate actions.
            try {
                CommandType type = command.extractType();
                switch (type) {
                case EXIT:
                    file.updateList(tasks);
                    System.out.println(BYE_MESSAGE);
                    break;
                case LIST:
                    listTasks();
                    break;
                case MARK_DONE:
                    itemNo = extractItemNo(command.getMessage(), DONE_TODO_LEN);
                    executeDone(itemNo);
                    break;
                case DELETE:
                    itemNo = extractItemNo(command.getMessage(), DELETE_LEN);
                    executeDelete(itemNo);
                    break;
                case TODO:
                    executeAdd(command.getMessage(), CommandType.TODO);
                    addMessage();
                    break;
                case DEADLINE:
                    executeAdd(command.getMessage(), CommandType.DEADLINE);
                    addMessage();
                    break;
                case EVENT:
                    executeAdd(command.getMessage(), CommandType.EVENT);
                    addMessage();
                    break;
                }
                if(type == CommandType.EXIT) {
                    printLine();
                    break;
                }
            } catch (DukeException | IOException error) {
                System.out.println(error);
            }
            printLine();
            command = new Command(in.nextLine());
        }

    }

    private static void printWelcomeMessage() {

        String logo = " __A__   _C__   _E__\n"
                    + "/  _  \\ /  __| |  __|\n"
                    + "| |_| | | |    | |__\n"
                    + "|  _  | | |__  | |__\n"
                    + "| | | | \\____| |____|\n";

        System.out.print(logo);
        printLine();

        System.out.println("\tHey there! I'm Ace, your very own personal assistant.");
        System.out.println("\tHow can I help you today?\n");
        printLine();

    }

    /** Adds the new task to the list.
     *
     * @param command task name given by the user.
     * @param type the type of task.
     * @throws DukeException duke error.
     */
    private static void executeAdd(String command, CommandType type) throws DukeException {

        switch (type) {
        case TODO:
            try {
                String todoName = command.substring(DONE_TODO_LEN).trim();
                tasks.add(new Todo(todoName));
            } catch (StringIndexOutOfBoundsException e) {
                throw new DukeException(ExceptionType.MISSING_DESCRIPTION);
            }
            break;
        case DEADLINE:
            String[] rawName = command.trim().split(DEADLINE_IDENTIFIER);
            checkCommand(command, rawName, "deadline", DEADLINE_IDENTIFIER);
            String deadlineName = rawName[0].substring(DEADLINE_LEN).trim();
            String by = rawName[1].trim();
            tasks.add(new Deadline(deadlineName, by));
            break;
        case EVENT:
            String[] rawEventName = command.trim().split(EVENT_IDENTIFIER);
            checkCommand(command, rawEventName, "event", EVENT_IDENTIFIER);
            String eventName = rawEventName[0].substring(EVENT_LEN).trim();
            String on = rawEventName[1].trim();
            tasks.add(new Event(eventName, on));
            break;
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
    private static void checkCommand(String command, String[] rawName, String taskName, String taskIdentifier) throws DukeException {
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

    /** Displays the appropriate message after adding task
     *
     */
    private static void addMessage() {

        System.out.println("\n\tAdded task \"" + tasks.get(Task.getNoOfTasks() - 1) + "\" to your To-Do's!");
        System.out.println("\tYou now have " + Task.getNoOfIncompleteTasks() + " incomplete task"
                + (Task.getNoOfIncompleteTasks() != 1 ? "s" : "") + ".");
        System.out.println("\tTo view the To-Do list simply type \"list\".\n");

    }

    /** Get the item number from the command entered.
     *
     * @param command the done command given.
     * @return the item number.
     */
    private static int extractItemNo(String command, int commandLen) {

        int itemNo;
        try {
            String rawItemNo = command.trim().substring(commandLen, command.length()).trim();
            if(rawItemNo.endsWith(".")) {
                rawItemNo = rawItemNo.substring(0,rawItemNo.length()-1);
            }
            itemNo = Integer.parseInt(rawItemNo);
        } catch (NumberFormatException error) {
            itemNo = 0;
        }
        return itemNo;

    }

    /** Mark the given item as done.
     *
     * @param itemNo index of the item to be marked as done.
     * @throws DukeException duke error.
     */
    private static void executeDone(int itemNo) throws DukeException {

        if(itemNo == 0) {
            throw new DukeException(ExceptionType.NOT_A_NUMBER);
        } else if(itemNo >Task.getNoOfTasks()) {
            System.out.println("\n\tItem " + itemNo + " does not exist.\n");
        } else if(tasks.get(itemNo -1).isDone()) {
            System.out.println("\n\tThis task was marked as done earlier!");
            System.out.println("\tTo see the list of incomplete tasks simply type \"list\".\n");
        } else {
            tasks.get(itemNo -1).markAsDone();
            System.out.println("\n\tGreat! I have updated your To-Do list for the following task:");
            System.out.println("\t\t " + tasks.get(itemNo -1) + "\n");
        }

    }

    /** Deletes the given item from the list.
     *
     * @param itemNo index of the item to be deleted.
     * @throws DukeException duke error.
     */
    private static void executeDelete(int itemNo) throws DukeException {
        if(itemNo == 0) {
            throw new DukeException(ExceptionType.NOT_A_NUMBER);
        } else if(itemNo >Task.getNoOfTasks()) {
            System.out.println("\n\tItem " + itemNo + " does not exist.\n");
        } else {
            Task taskToDelete = tasks.get(itemNo -1);
            if(!taskToDelete.isDone()) {
                Task.setNoOfIncompleteTasks(Task.getNoOfIncompleteTasks() - 1);
            }
            Task.setNoOfTasks(Task.getNoOfTasks() - 1);
            tasks.remove(itemNo -1);
            System.out.println("\n\tOkie! I have removed the following task:");
            System.out.println("\t\t " + taskToDelete);
            System.out.println("\tYou now have " + Task.getNoOfIncompleteTasks() + " incomplete task"
                    + (Task.getNoOfIncompleteTasks() != 1 ? "s" : "") + "." + "\n");

        }
    }

    private static void listTasks() {

        if(Task.getNoOfTasks()==0) {
            System.out.println("\n\tYou currently don't have any tasks.");
            System.out.println("\tTo add a task simply input the task description.\n");
        } else {
            System.out.println("\n\tHere's your current To-Do list:");
            for (int i = 0; i<Task.getNoOfTasks(); i += 1) {
                System.out.println("\t\t"+ (i+1) + ". " + tasks.get(i));
            }
            System.out.println();
        }

    }

}
