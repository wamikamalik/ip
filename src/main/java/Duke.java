import  java.util.Scanner;

public class Duke {

    public static final String BYE_MESSAGE = "\n\tSee you! Have a nice day!\n";
    public static final String DEADLINE_IDENTIFIER = "/by";
    public static final String EVENT_IDENTIFIER = "/on";

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

        printWelcomeMessage();

        Scanner in = new Scanner(System.in);
        Command command = new Command(in.nextLine());
        Task[] tasks = new Task[100];

        while(true) {
            //the following code interprets the command entered by the user and takes appropriate actions.
            try {
                CommandType type = command.extractType();
                switch (type) {
                case EXIT:
                    System.out.println(BYE_MESSAGE);
                    break;
                case LIST:
                    listTasks(tasks);
                    break;
                case MARK_DONE:
                    int itemNo = extractItemNo(command.getMessage());
                    executeDone(tasks, itemNo);
                    break;
                case TODO:
                    executeAdd(command.getMessage(), tasks, CommandType.TODO);
                    addMessage(tasks);
                    break;
                case DEADLINE:
                    executeAdd(command.getMessage(), tasks, CommandType.DEADLINE);
                    addMessage(tasks);
                    break;
                case EVENT:
                    executeAdd(command.getMessage(), tasks, CommandType.EVENT);
                    addMessage(tasks);
                    break;
                }
                if(type == CommandType.EXIT) {
                    printLine();
                    break;
                }
            } catch (DukeException error) {
                System.out.println(error);
            }
            printLine();
            command = new Command(in.nextLine());
        }

    }

    /** Print the welcome massage.
     *
     */
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
     * @param command task name given by the user
     * @param tasks the list of tasks
     * @param type the type of task
     */
    private static void executeAdd(String command, Task[] tasks, CommandType type) throws DukeException {

        switch (type) {
        case TODO:
            try {
                String todoName = command.substring(5).trim();
                tasks[Task.getNoOfTasks()] = new Todo(todoName);
            } catch (StringIndexOutOfBoundsException e) {
                throw new DukeException(ExceptionType.MISSING_DESCRIPTION);
            }
            break;
        case DEADLINE:
            String[] rawName = command.trim().split(DEADLINE_IDENTIFIER);
            if (rawName[0].trim().equalsIgnoreCase("deadline")) {
                throw new DukeException(ExceptionType.MISSING_DESCRIPTION);
            }
            if (!command.contains(DEADLINE_IDENTIFIER)) {
                throw new DukeException(ExceptionType.MISSING_IDENTIFIER);
            }
            if(rawName.length<2) {
                throw new DukeException(ExceptionType.MISSING_ON_BY);
            }
            String deadlineName = rawName[0].substring(9).trim();
            String by = rawName[1].trim();
            tasks[Task.getNoOfTasks()] = new Deadline(deadlineName, by);
            break;
        case EVENT:
            String[] rawEventName = command.trim().split(EVENT_IDENTIFIER);
            if (rawEventName[0].trim().equalsIgnoreCase("event")) {
                throw new DukeException(ExceptionType.MISSING_DESCRIPTION);
            }
            if (!command.contains(EVENT_IDENTIFIER)) {
                throw new DukeException(ExceptionType.MISSING_IDENTIFIER);
            }
            if(rawEventName.length<2) {
                throw new DukeException(ExceptionType.MISSING_ON_BY);
            }
            String eventName = rawEventName[0].substring(6).trim();
            String on = rawEventName[1].trim();
            tasks[Task.getNoOfTasks()] = new Event(eventName, on);
            break;
        }
    }

    /**displays the appropriate message after adding task
     *
     * @param tasks the list of tasks
     */
    private static void addMessage(Task[] tasks) {

        System.out.println("\n\tAdded task \"" + tasks[Task.getNoOfTasks() - 1] + "\" to your To-Do's!");
        System.out.println("\tYou now have " + Task.getNoOfIncompleteTasks() + " incomplete task"
                + (Task.getNoOfIncompleteTasks() != 1 ? "s" : "") + ".");
        System.out.println("\tTo view the To-Do list simply type \"list\".\n");

    }

    /** Get the item number from the command entered.
     *
     * @param command the done command given
     * @return the item number
     */
    private static int extractItemNo(String command) {

        int itemNo;
        try {
            String rawItemNo = command.trim().substring(5, command.length()).trim();
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
     * @param tasks the list of tasks
     * @param itemNo the item to be deleted
     */
    private static void executeDone(Task[] tasks, int itemNo) throws DukeException {

        if(itemNo == 0) {
            throw new DukeException(ExceptionType.NOT_A_NUMBER);
        } else if(itemNo >Task.getNoOfTasks()) {
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

    /** List the tasks.
     *
     * @param tasks list of tasks
     */
    private static void listTasks(Task[] tasks) {

        if(Task.getNoOfTasks()==0) {
            System.out.println("\n\tYou currently don't have any tasks.");
            System.out.println("\tTo add a task simply input the task description.\n");
        } else {
            System.out.println("\n\tHere's your current To-Do list:");
            for (int i = 0; i<Task.getNoOfTasks(); i += 1) {
                System.out.println("\t\t"+ (i+1) + ". " + tasks[i]);
            }
            System.out.println();
        }

    }

}
