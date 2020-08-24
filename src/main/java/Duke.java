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
                        System.out.println("\t\t"+(i+1)+". " + taskList[i].getName());
                    }
                    System.out.println();
                }

            }
            else {

                Task newItem = new Task(command);
                taskList[Task.getNoOfTasks() - 1] = newItem;
                System.out.println("\n\tAdded task \"" + newItem.getName() + "\" to your To-Do's!");
                System.out.println("\tYou now have " + Task.getNoOfTasks() + " incomplete task"
                        + (Task.getNoOfTasks() != 1 ? "s" : "") + ".\n");
                
            }
            printLine();
            command = in.nextLine();
        }
    }
}
