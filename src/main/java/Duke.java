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
        while(!command.equalsIgnoreCase("bye")) {
            System.out.println("\n\t"+command+"\n");
            printLine();
            command = in.nextLine();
        }
        System.out.println("\n\tSee you! Have a nice day!\n");
        printLine();
    }
}
