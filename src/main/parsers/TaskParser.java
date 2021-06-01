package parsers;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

// Represents Task parser
public class TaskParser {

    // EFFECTS: iterates over every JSONObject in the JSONArray represented by the input
    // string and parses it as a task; each parsed task is added to the list of tasks.
    // Any task that cannot be parsed due to malformed JSON data is not added to the
    // list of tasks.
    // Note: input is a string representation of a JSONArray
    public List<Task> parse(String input) {

        List<Task> tasks = new ArrayList<>();
        JSONArray tasksArray = new JSONArray(input);

        for (Object object : tasksArray) {

            JSONObject taskJson = (JSONObject) object;
            if (isValid(taskJson)) {
                Task task = new Task(taskJson.getString("description"));
                setUp(taskJson, task);
                tasks.add(task);
            }
        }
        return tasks;
    }

    private void setUp(JSONObject taskJson, Task task) {
        JSONArray tagsArray = taskJson.getJSONArray("tags");
        setTag(tagsArray, task);

        if (!taskJson.isNull("due-date")) {
            JSONObject dueDate = taskJson.getJSONObject("due-date");
            setDueDate(dueDate, task);
        }

        JSONObject priority = taskJson.getJSONObject("priority");
        setPriority(priority, task);

        task.setStatus(Status.valueOf(taskJson.getString("status")));
    }

    private boolean isValid(JSONObject taskJson) {
        if (!taskJson.has("description")
                || !(taskJson.get("description") instanceof String)) {
            return false;
        }

        if (!validPriority(taskJson)) {
            return false;
        }

        if (!taskJson.has("status")
                || !(taskJson.get("status") instanceof String)) {
            return false;
        }

        if (!validDueDate(taskJson)) {
            return false;
        }

        if (!validTags(taskJson)) {
            return false;
        }

        return true;
    }

    private boolean validTags(JSONObject taskJson) {
        if (!taskJson.has("tags")) {
            return false;
        }
        if (!(taskJson.get("tags") instanceof JSONArray)) {
            return false;
        }

        JSONArray tagsArray = taskJson.getJSONArray("tags");
        for (int i = 0; i < tagsArray.length(); i++) {
            JSONObject c = tagsArray.getJSONObject(i);
            if (!c.has("name")) {
                return false;
            }
            if (!(c.get("name") instanceof String)) {
                return false;
            }
        }


        return true;
    }

    private boolean validPriority(JSONObject taskJson) {
        if (taskJson.has("priority")) {
            if (taskJson.getJSONObject("priority").has("important")
                    && taskJson.getJSONObject("priority").has("urgent")) {
                if (taskJson.getJSONObject("priority").get("important") instanceof Boolean
                        && taskJson.getJSONObject("priority").get("urgent") instanceof Boolean) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean validDueDate(JSONObject taskJson) {

        if (!taskJson.has("due-date")) {
            return false;
        }

        if (taskJson.isNull("due-date")) {
            return true;
        }

        if (validElement(taskJson)) {
            if (taskJson.getJSONObject("due-date").get("year") instanceof Integer
                    && taskJson.getJSONObject("due-date").get("month") instanceof Integer
                    && taskJson.getJSONObject("due-date").get("day") instanceof Integer
                    && taskJson.getJSONObject("due-date").get("hour") instanceof Integer
                    && taskJson.getJSONObject("due-date").get("minute") instanceof Integer) {
                return true;
            }
        }
        return false;
    }

    private boolean validElement(JSONObject taskJson) {
        if (taskJson.getJSONObject("due-date").has("year")
                && taskJson.getJSONObject("due-date").has("month")
                && taskJson.getJSONObject("due-date").has("day")
                && taskJson.getJSONObject("due-date").has("hour")
                && taskJson.getJSONObject("due-date").has("minute")) {
            return true;
        }
        return false;
    }

    private void setDueDate(JSONObject dueDate, Task task) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(dueDate.getInt("year"), dueDate.getInt("month"),
                dueDate.getInt("day"), dueDate.getInt("hour"), dueDate.getInt("minute"));
        Date date = calendar.getTime();
        DueDate dd = new DueDate();
        dd.setDueDate(date);
        task.setDueDate(dd);
    }

    private void setPriority(JSONObject priority, Task task) {
        Boolean isImportant = priority.getBoolean("important");
        Boolean isUrgent = priority.getBoolean("urgent");
        Priority p = new Priority();
        p.setUrgent(isUrgent);
        p.setImportant(isImportant);
        task.setPriority(p);
    }

    private void setTag(JSONArray tagsArray, Task task) {
        for (int i = 0; i < tagsArray.length(); i++) {
            JSONObject c = tagsArray.getJSONObject(i);
            task.addTag(c.getString("name"));
        }
    }


}
