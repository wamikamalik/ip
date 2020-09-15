import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.Todo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileManager {

    private static String filename;
    private static String dir;
    private static File file;

    public FileManager(String name, String dir) {
        filename = name;
        this.dir = dir;
        createFile();
    }

    private void createFile() {
        file = new File(dir + '/' + filename);
        try {
            if(!file.exists()) {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();
                System.out.println("File created: " + file.getAbsolutePath());
            }
        } catch (IOException e) {
            System.out.println("\n\tYikes! I could not create a file to store your data. Reason:" + e.getMessage());
        }
    }

    public void loadData(Task[] tasks) throws FileNotFoundException {
        Scanner s = new Scanner(file); // create a Scanner using the File as the source
        int taskNo = 0;
        while (s.hasNext()) {
            String line = s.nextLine();
            String[] details = line.split("\\|");
            switch (details[0].trim()) {
            case "T":
                tasks[taskNo] = new Todo(details[2].trim());
                break;
            case "D":
                tasks[taskNo] = new Deadline(details[2].trim(), details[3].trim());
                break;
            case "E":
                 tasks[taskNo] = new Event(details[2].trim(), details[3].trim());
            }
            if(details[1].trim().equals("1")) {
                tasks[taskNo].markAsDone();
            }
            taskNo+=1;
        }
    }

    public void updateList(Task[] tasks) throws IOException {
        FileWriter fw = new FileWriter(file);
        String description;
        String done;
        String type;
        String textToAdd = "";

        for(int i = 0; i<Task.getNoOfTasks(); i++) {
            Task task = tasks[i];
            type = generateType(task);
            done = generateDone(task);
            description = task.getName();
            textToAdd += type + " | " + done + " | " + description;
            if(!(task instanceof Todo)) {
                String time;
                if(task instanceof Event) {
                    time = ((Event) task).getTime();
                } else {
                    time = ((Deadline) task).getBy();
                }
                textToAdd += " | " + time;
            }
            textToAdd += System.lineSeparator();
        }

        fw.write(textToAdd);
        fw.close();
    }

    private String generateDone(Task task) {
        String done;
        if(task.isDone()) {
            done = "1";
        } else {
            done = "0";
        }
        return done;
    }

    private String generateType(Task task) {
        String type;
        if(task instanceof Todo) {
            type = "T";
        } else if(task instanceof Event) {
            type = "E";
        } else {
            type = "D";
        }
        return type;
    }

}
