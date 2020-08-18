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
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.print(logo);
        printLine();
        System.out.println(" Hello! I'm Duke");
        System.out.println(" What can I do for you?\n");
        printLine();
        System.out.println(" Bye. Hope to see you again soon!\n");
        printLine();
    }
}
