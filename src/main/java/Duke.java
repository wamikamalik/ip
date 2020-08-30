import  java.util.Scanner;

public class Duke {

    /**
     * prints a line of 100 character length.
     *
     */
    public static void printLine() {

        for(int i = 0; i<100; i++) {
            System.out.print("-");
        }
        System.out.println();

    }

    public static void main(String[] args) {

        welcomeMessage();

        Scanner in = new Scanner(System.in);
        Command command = new Command(in.nextLine());
        Task[] tasks = new Task[100];

        while(true) {
            //the following code interprets the command entered by the user and takes appropriate actions.
            CommandType type = command.extractType();
            switch (type) {
                case EXIT:
                    System.out.println("\n\tSee you! Have a nice day!\n");
                    break;
                case LIST:
                    listTasks(tasks);
                    break;
                case MARK_DONE:
                    int itemNo = extractItemNo(command.getMessage());
                    executeDone(tasks, itemNo);
                    break;
                case ADD:
                    executeAdd(command.getMessage(), tasks);
                    break;
                default:
                    System.out.println("Sorry :( I don't understand.");
            }
            printLine();
            if(type == CommandType.EXIT) {
                break;
            }
            command = new Command(in.nextLine());
        }

    }

    /** Print the welcome massage.
     *
     */
    private static void welcomeMessage() {
        String logo = " __A__   _C__   _E__\n"
                    + "/  _  \\ /  __| |  __|\n"
                    + "| |_| | | |    | |__   \n"
                    + "|  _  | | |__  | |__ \n"
                    + "| | | | \\____| |____|\n";

        System.out.print(logo);
        printLine();

        System.out.println("\tHey there! I'm Ace, your very own personal assistant.");
        System.out.println("\tHow can I help you today?\n");
        printLine();
    }

    /** Adds the new task to the list.
     *
     * @param command task name given by the user
     * @param tasks the list of tasks
     */
    private static void executeAdd(String command, Task[] tasks) {

        Task newItem = new Task(command);
        tasks[Task.getNoOfTasks() - 1] = newItem;
        System.out.println("\n\tAdded task \"" + newItem.getName() + "\" to your To-Do's!");
        System.out.println("\tYou now have " + Task.getnoOfIncompleteTasks() + " incomplete task"
                + (Task.getnoOfIncompleteTasks() != 1 ? "s" : "") + ".");
        System.out.println("\tTo view the To-Do list simply type \"list\".\n");

    }

    /** Mark the given item as done.
     *
     * @param tasks the list of tasks
     * @param itemNo the item to be deleted
     */
    private static void executeDone(Task[] tasks, int itemNo) {

        if(itemNo ==0) {
            System.out.println("\n\tPlease enter a valid item number.\n");
        }else if(itemNo >Task.getNoOfTasks()) {
            System.out.println("\n\tItem "+ itemNo +" does not exist.\n");
        } else if(tasks[itemNo -1].isDone()) {
            System.out.println("\n\tThis task was marked as done earlier!");
            System.out.println("\tTo see the list of incomplete tasks simply type \"list\".\n");
        } else {
            tasks[itemNo -1].markAsDone();
            System.out.println("\n\tGreat! I have updated your To-Do list for the following task:");
            System.out.println("\t\t ["+ tasks[itemNo -1].getIcon() + "] "
                    + tasks[itemNo -1].getName()+"\n");
        }

    }

    /** Get the item number from the command entered.
     *
     * @param command the done command given
     * @return the item number
     */
    private static int extractItemNo(String command) {

        int itemNo;
        try {
            String rawItemNo = command.trim().substring(5, command.length());
            if(rawItemNo.endsWith(".")) {
                rawItemNo = rawItemNo.substring(0,rawItemNo.length()-1);
            }
            itemNo = Integer.parseInt(rawItemNo);
        } catch (NumberFormatException error) {
            itemNo = 0;
        }
        return itemNo;

    }

    /** List the tasks.
     *
     * @param tasks list of tasks
     */
    private static void listTasks(Task[] tasks) {

        if(Task.getNoOfTasks()==0) {
            System.out.println("\n\tYou currently don't have any tasks.");
            System.out.println("\tTo add a task simply input the task description.\n");
        } else {
            System.out.println("\n\tHere's your current To-Do list: ");
            for (int i = 0; i<Task.getNoOfTasks(); i += 1) {
                System.out.println("\t\t"+ (i+1) +". [" + tasks[i].getIcon() + "] "
                        + tasks[i].getName());
            }
            System.out.println();
        }

    }

}
