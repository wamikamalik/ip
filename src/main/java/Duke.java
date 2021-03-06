import duke.Storage.FileManager;
import duke.task.TaskList;
import duke.command.Parser;
import duke.task.Task;

import duke.command.CommandType;
import duke.exception.DukeException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Represents the main class of Duke.
 */
public class Duke {

    public static final int DONE_TODO_LEN = 5;
    public static final int DELETE_LEN = 7;
    private static final String FILE = "duke.txt";
    private static final String DIR = "data";

    private TaskList tasks;
    private Ui ui;
    private FileManager file;

    public Duke() {
        ui = new Ui();
        file = new FileManager(FILE, DIR);
        try {
            tasks = new TaskList(file.loadData());
        } catch (FileNotFoundException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    public static void main(String[] args) {
        new Duke().run();
    }

    /**
     * Execute the flow of the personal assistant.
     */
    public void run() {
        ui.printWelcomeMessage();
        boolean isExit = false;
        int itemNo;
        
        ArrayList<Task> tasksToday = tasks.getToday();
        TaskList dueToday = new TaskList(tasksToday);
        ui.listTasksForToday(dueToday);
        ui.printLine();

        while(!isExit) {
            //the following code interprets the command entered by the user and takes appropriate actions.
            try {
                String fullCommand = ui.getCommand();
                Parser parser = new Parser(fullCommand);
                CommandType type = parser.extractType();
                switch (type) {
                case EXIT:
                    isExit = true;
                    ui.printByeMessage();
                    break;
                case LIST:
                    ui.listTasks(tasks);
                    break;
                case MARK_DONE:
                    itemNo = parser.extractItemNo(DONE_TODO_LEN);
                    Task setDone = tasks.done(itemNo);
                    ui.doneMessage(setDone);
                    break;
                case DELETE:
                    itemNo = parser.extractItemNo(DELETE_LEN);
                    Task toDelete = tasks.delete(itemNo);
                    ui.deleteMessage(toDelete);
                    break;
                case FIND:
                    String keyword = parser.extractKeyword();
                    TaskList tasksFound = new TaskList(tasks.find(keyword));
                    ui.listMatchingTasks(tasksFound);
                    break;
                case HELP:
                    ui.printHelp();
                    break;
                case TODO:
                    tasks.add(parser, fullCommand, CommandType.TODO);
                    ui.addMessage(tasks);
                    break;
                case DEADLINE:
                    tasks.add(parser, fullCommand, CommandType.DEADLINE);
                    ui.addMessage(tasks);
                    break;
                case EVENT:
                    tasks.add(parser, fullCommand, CommandType.EVENT);
                    ui.addMessage(tasks);
                    break;
                }
            } catch (DukeException error) {
                ui.showError(error);
            }
            ui.printLine();
        }
        try {
            file.updateList(tasks);
        } catch (IOException error) {
            ui.showError(error);
        }
    }


}
