import duke.task.TaskList;
import duke.task.Task;

import java.util.Scanner;

/**
 * Represents all the interaction that happens with the user.
 */
public class Ui {

    private Scanner in = new Scanner(System.in);

    public void printLine() {

        for(int i = 0; i<100; i++) {
            System.out.print("-");
        }
        System.out.println();

    }

    public void showLoadingError() {
        System.out.println("\t\nThe World has gone crazy!");
    }

    public void printWelcomeMessage() {

        String logo = " __A__   _C__   _E__\n"
                + "/  _  \\ /  __| |  __|\n"
                + "| |_| | | |    | |__\n"
                + "|  _  | | |__  | |__\n"
                + "| | | | \\____| |____|\n";

        System.out.print(logo);
        printLine();

        System.out.println("\tHey there! I'm Ace, your very own personal assistant.");
        System.out.println("\tHow can I help you today?\n");

    }

    public String getCommand() {
        String command = in.nextLine();
        return command;
    }

    public void showError(Exception error) {
        System.out.println(error);
    }

    public  void printByeMessage() {
        System.out.println("\n\tSee you! Have a nice day!\n");
    }

    /**
     * Displays the appropriate message after adding task.
     * @param tasks the list of tasks to which a new task has been added.
     */
    public void addMessage(TaskList tasks) {

        System.out.println("\n\tAdded task \"" + tasks.get(Task.getNoOfTasks() - 1) + "\" to your To-Do's!");
        System.out.println("\tYou now have " + Task.getNoOfIncompleteTasks() + " incomplete task"
                + (Task.getNoOfIncompleteTasks() != 1 ? "s" : "") + ".");
        System.out.println("\tTo view the To-Do list simply type \"list\".\n");

    }

    /**
     * Displays the appropriate message after deleting a task.
     *
     * @param taskToDelete the task that has been deleted.
     */
    public void deleteMessage(Task taskToDelete) {

        System.out.println("\n\tOkie! I have removed the following task:");
        System.out.println("\t\t " + taskToDelete);
        System.out.println("\tYou now have " + Task.getNoOfIncompleteTasks() + " incomplete task"
                + (Task.getNoOfIncompleteTasks() != 1 ? "s" : "") + "." + "\n");

    }

    /**
     * Displays the appropriate message after marking a task as done.
     *
     * @param setDone the task that has been marked done.
     */
    public void doneMessage(Task setDone) {
        System.out.println("\n\tGreat! I have updated your To-Do list for the following task:");
        System.out.println("\t\t " + setDone + "\n");
    }

    /**
     * Displays the list of tasks.
     *
     * @param tasks tasks to be listed.
     */
    public void listTasks(TaskList tasks) {

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

    public void listTasksForToday(TaskList dueToday) {
        if(dueToday.size()==0) {
            System.out.println("\n\tYou don't have any tasks or events due today ;)\n");
        } else {
            System.out.println("\n\tThese are your deadlines and events for today:");
            for (int i = 0; i<dueToday.size(); i += 1) {
                System.out.println("\t\t"+ (i+1) + ". " + dueToday.get(i));
            }
            System.out.println();
        }
    }
}
