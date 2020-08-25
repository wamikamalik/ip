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
        Scanner in = new Scanner(System.in);
        String command = in.nextLine();
        Task[] taskList = new Task[100];
        while(true) {
            if(command.equalsIgnoreCase("bye")) {

                System.out.println("\n\tSee you! Have a nice day!\n");
                printLine();
                break;

            }
            else if(command.equalsIgnoreCase("list")) {

                if(Task.getNoOfTasks()==0) {
                    System.out.println("\n\tYou currently don't have any tasks.");
                    System.out.println("\tTo add a task simply input the task description.\n");
                }
                else {
                    System.out.println("\n\tHere's your current To-Do list: ");
                    for (int i = 0; i<Task.getNoOfTasks(); i += 1) {
                        System.out.println("\t\t"+ (i+1) +". [" + taskList[i].getIcon() + "] " + taskList[i].getName());
                    }
                    System.out.println();
                }

            }
            else if(command.trim().startsWith("done")||command.trim().startsWith("Done")) {
                try {
                    String rawItemNo = command.trim().substring(5, command.length());
                    if(rawItemNo.endsWith(".")) {
                        rawItemNo = rawItemNo.substring(0,rawItemNo.length()-1);
                    }
                    int itemNo = Integer.parseInt(rawItemNo);
                    //System.out.println("\n\t" + itemNo + "\n");
                    if(itemNo==0 || itemNo>Task.getNoOfTasks()) {
                        System.out.println("\n\tItem "+ itemNo +" does not exist.\n");
                    }
                    else if(taskList[itemNo-1].isDone()) {
                        System.out.println("\n\tThis task was marked as done earlier!");
                        System.out.println("\tTo see the list of incomplete tasks simply type \"list\".\n");
                    }
                    else {
                        taskList[itemNo-1].markAsDone();
                        System.out.println("\n\tGreat! I have updated your To-Do list for the following task:");
                        System.out.println("\t\t ["+ taskList[itemNo-1].getIcon() + "] " + taskList[itemNo-1].getName()+"\n");
                    }

                }
                catch (NumberFormatException error) {
                    System.out.println("\n\tPlease enter a valid item number.\n");
                }
            }
            else {

                Task newItem = new Task(command);
                taskList[Task.getNoOfTasks() - 1] = newItem;
                System.out.println("\n\tAdded task \"" + newItem.getName() + "\" to your To-Do's!");
                System.out.println("\tYou now have " + Task.getIncompleteTasks() + " incomplete task"
                        + (Task.getIncompleteTasks() != 1 ? "s" : "") + ".");
                System.out.println("\tTo view the To-Do list simply type \"list\".\n");
                
            }
            printLine();
            command = in.nextLine();
        }
    }
}
