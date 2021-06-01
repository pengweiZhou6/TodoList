package utility;

import model.Task;
import org.json.JSONArray;
import parsers.TaskParser;
import persistence.Jsonifier;

import java.io.*;
import java.util.List;

// File input/output operations
public class JsonFileIO {
    public static final File jsonDataFile = new File("./resources/json/tasks.json");

    // EFFECTS: attempts to read jsonDataFile and parse it
    //           returns a list of tasks from the content of jsonDataFile
    public static List<Task> read() throws IOException {
        TaskParser taskParser = new TaskParser();
        BufferedReader br = new BufferedReader(new FileReader(jsonDataFile));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            return taskParser.parse(sb.toString());
        } finally {
            br.close();
        }
    }

    // EFFECTS: saves the tasks to jsonDataFile
    public static void write(List<Task> tasks) throws IOException {
        JSONArray tasksArray = Jsonifier.taskListToJson(tasks);
        try (FileWriter file = new FileWriter(jsonDataFile)) {
            file.write(tasksArray.toString());
        }
    }
}
