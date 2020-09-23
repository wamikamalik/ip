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

    public void listMatchingTasks(TaskList tasksFound) {
        if(tasksFound.size()==0) {
            System.out.println("\n\tOoPsIEs! No matching tasks found.\n");
        } else {
            System.out.println("\n\tThese are the matching tasks in your list:");
            for (int i = 0; i<tasksFound.size(); i += 1) {
                System.out.println("\t\t"+ (i+1) + ". " + tasksFound.get(i));
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

    /**
     * Displays the help data to the user.
     */
    public void printHelp() {
        System.out.println("\n\tThese are the list of commands you can use:\n");
        System.out.println("\t\t1. help\n\t\tProvides a list of commands you can enter.\n");
        System.out.println("\t\t2. list\n\t\tProvides a list of your tasks.\n");
        System.out.println("\t\t3. todo <taskname>\n\t\tAdds a todo to the list with name <taskname>." +
                "\n\t\texample: todo read book\n");
        System.out.println("\t\t4. deadline <taskname> /by <date>\n\t\tAdds a deadline to the list with name <taskname>"
                + " and deadline <date>." + "\n\t\texample: deadline return book /by 30-10-2020\n\t\t\t\t OR\n\t\t\t\t "
                + "deadline return book " + "/by 30-10-2020 14:35\n");
        System.out.println("\t\t5. event <taskname> /on <date>\n\t\tAdds an event to the list with name <taskname> and"
                + " event date <date>." + "\n\t\texample: event book show /on 30-10-2020\n\t\t\t\t OR\n\t\t\t\t "
                + "event book show " + "/on 30-10-2020 14:35\n");
        System.out.println("\t\t6. done <tasknumber>\n\t\tMarks the task with number <tasknumber> as done." +
                "\n\t\texample: done 3\n");
        System.out.println("\t\t7. delete <tasknumber>\n\t\tDeletes the task with number <tasknumber>." +
                "\n\t\texample: delete 3\n");
        System.out.println("\t\t8. find <keyword>\n\t\tLists the tasks that contain the <keyword>." +
                "\n\t\texample: find book\n");
        System.out.println("\t\t9. bye\n\t\tExits the application.\n");
    }
}
