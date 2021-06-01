package persistence;


import model.DueDate;
import model.Priority;
import model.Tag;
import model.Task;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.List;

// Converts model elements to JSON objects
public class Jsonifier {

    // EFFECTS: returns JSON representation of tag
    public static JSONObject tagToJson(Tag tag) {
        JSONObject tagJson = new JSONObject();
        tagJson.put("name", tag.getName());
        return tagJson;
    }

    // EFFECTS: returns JSON representation of priority
    public static JSONObject priorityToJson(Priority priority) {
        JSONObject priorityJson = new JSONObject();
        priorityJson.put("important", priority.isImportant());
        priorityJson.put("urgent", priority.isUrgent());
        return priorityJson;
    }

    // EFFECTS: returns JSON representation of dueDate
    public static JSONObject dueDateToJson(DueDate dueDate) {
        if (dueDate == null) {
            return null;
        }
        JSONObject dueDateJson = new JSONObject();
        Calendar cal = Calendar.getInstance();
        cal.setTime(dueDate.getDate());
        dueDateJson.put("year", cal.get(Calendar.YEAR));
        dueDateJson.put("month", cal.get(Calendar.MONTH));
        dueDateJson.put("day", cal.get(Calendar.DAY_OF_MONTH));
        dueDateJson.put("hour", cal.get(Calendar.HOUR_OF_DAY));
        dueDateJson.put("minute", cal.get(Calendar.MINUTE));
        return dueDateJson;
    }

    // EFFECTS: returns JSON representation of task
    public static JSONObject taskToJson(Task task) {
        JSONObject taskJson = new JSONObject();
        JSONArray tagsArray = new JSONArray();


        for (Tag t : task.getTags()) {
            tagsArray.put(tagToJson(t));
        }
        taskJson.put("description", task.getDescription());
        taskJson.put("tags", tagsArray);
        if (task.getDueDate() == null) {
            taskJson.put("due-date", JSONObject.NULL);
        } else {
            taskJson.put("due-date", dueDateToJson(task.getDueDate()));
        }
        taskJson.put("priority", priorityToJson(task.getPriority()));
        taskJson.put("status", task.getStatus().toString().replaceAll("\\s+", "_"));

        return taskJson;
    }

    // EFFECTS: returns JSON array representing list of tasks
    public static JSONArray taskListToJson(List<Task> tasks) {
        JSONArray tasksArray = new JSONArray();
        for (Task t : tasks) {
            tasksArray.put(taskToJson(t));
        }
        return tasksArray;
    }
}
