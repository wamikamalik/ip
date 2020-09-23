package duke.Storage;

import duke.exception.DukeException;
import duke.task.TaskList;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.Todo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents the file where data is stored and from which data is retrieved.
 */
public class FileManager {

    private String filename;
    private String dir;
    private File file;

    public FileManager(String name, String dir) {
        filename = name;
        this.dir = dir;
        createFile();
    }

    /**
     * Creates a new file if the file does not exist.
     * Opens a file if it exists.
     */
    private void createFile() {
        file = new File(dir + '/' + filename);
        try {
            if(!file.exists()) {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("\n\tYikes! I could not create a file to store your data. Reason:" + e.getMessage());
        }
    }

    /**
     * Loads the data stored in the file and store it in an ArrayList.
     *
     * @return the ArrayList of data generated from the file.
     * @throws FileNotFoundException If the file is not found.
     */
    public ArrayList<Task> loadData() throws FileNotFoundException {
        ArrayList<Task> tasks = new ArrayList<>();
        Scanner s = new Scanner(file); // create a Scanner using the File as the source
        int taskNo = 0;
        while (s.hasNext()) {
            String line = s.nextLine();
            String[] details = line.split("\\|");
            switch (details[0].trim()) {
            case "T":
                tasks.add(new Todo(details[2].trim()));
                break;
            case "D":
                tasks.add(new Deadline(details[2].trim(), details[3].trim()));
                break;
            case "E":
                tasks.add(new Event(details[2].trim(), details[3].trim()));
            }
            if (details[1].trim().equals("1")) {
                tasks.get(taskNo).markAsDone();
            }
            taskNo += 1;
        }
        return tasks;
    }

    /**
     * Updates the text file according to the new task list.
     *
     * @param tasks the new list of tasks.
     * @throws IOException If there is a problem writing into the file.
     */
    public void updateList(TaskList tasks) throws IOException {
        FileWriter fw = new FileWriter(file);
        String description;
        String done;
        String type;
        String textToAdd = "";

        for(Task task: tasks) {
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
