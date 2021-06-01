package model;

import model.exceptions.EmptyStringException;
import model.exceptions.NullArgumentException;

import java.util.*;

// Represents a Project, a collection of zero or more Tasks
// Class Invariant: no duplicated task; order of tasks is preserved
public class Project extends Todo implements Iterable<Todo> {
    private String description;
    private List<Todo> tasks;
    private int totalTime;
    private int totalProgress;

    // MODIFIES: this
    // EFFECTS: constructs a project with the given description
    //     the constructed project shall have no tasks.
    //  throws EmptyStringException if description is null or empty
    public Project(String description) {
        super(description);
        this.description = description;
        tasks = new ArrayList<>();
    }

    public void add(Todo t) {
        if (t == null) {
            throw new NullArgumentException("Null Argument!!!");
        }
        if (!contains(t) && !t.equals(this)) {
            tasks.add(t);
        }
    }

    public void remove(Todo t) {
        if (t == null) {
            throw new NullArgumentException("Null Argument!!!");
        }
        if (contains(t)) {
            tasks.remove(t);
        }
    }

    // EFFECTS: returns the description of this project
    public String getDescription() {
        return description;
    }

    @Override
    public int getEstimatedTimeToComplete() {
        totalTime = 0;
        for (Todo t : tasks) {
            totalTime = totalTime + t.getEstimatedTimeToComplete();
        }
        return totalTime;
    }

    @Override
// EFFECTS: returns an integer between 0 and 100 which represents
//     the percentage of completion (rounded down to the nearest integer).
//     the value returned is the average of the percentage of completion of
//     all the tasks and sub-projects in this project.
    public int getProgress() {
        totalProgress = 0;
        if (tasks.isEmpty()) {
            return 0;
        }
        for (Todo t : tasks) {
            totalProgress = totalProgress + t.getProgress();
        }
        return (totalProgress / getNumberOfTasks());
    }


    // EFFECTS: returns an unmodifiable list of tasks in this project.
    @Deprecated
    public List<Task> getTasks() {
        throw new UnsupportedOperationException();
    }

    // EFFECTS: returns the number of tasks in this project
    public int getNumberOfTasks() {
        return tasks.size();
    }

    // EFFECTS: returns true if every task (and sub-project) in this project is completed, and false otherwise
//     If this project has no tasks (or sub-projects), return false.
    public boolean isCompleted() {
        return getNumberOfTasks() != 0 && getProgress() == 100;
    }

    // EFFECTS: returns true if this project contains the task
    //   throws NullArgumentException when task is null
    public boolean contains(Todo t) {
        if (t == null) {
            throw new NullArgumentException("Illegal argument: task is null");
        }
        return tasks.contains(t);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Project)) {
            return false;
        }
        Project project = (Project) o;
        return Objects.equals(description, project.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }

    @Override
    public Iterator<Todo> iterator() {
        return new ProjectIterator();
    }

    private class ProjectIterator implements Iterator<Todo> {
        private int listIndex;
        private int priorityNum;
        private Priority currentP;

        // EFFECTS: constructs iterator
        ProjectIterator() {
            listIndex = 0;
            priorityNum = 1;
            currentP = new Priority(priorityNum);
        }

        @Override
        public boolean hasNext() {
            return listIndex < tasks.size();
        }

        @Override
        public Todo next() {
            if (!hasNext()) {
                listIndex = 0;
                priorityNum = priorityNum + 1;
                if (priorityNum > 4) {
                    throw new NoSuchElementException();
                }
                currentP = new Priority(priorityNum);
            }
            Todo t = getNext();
            return t;
        }

        private Todo getNext() {
            for (int i = listIndex; i < tasks.size(); i++) {
                if (match(i)) {
                    listIndex = i + 1;
                    return tasks.get(i);
                }
            }
            listIndex = tasks.size();
            return next();
        }

        private boolean match(int i) {
            Todo t = tasks.get(i);
            return t.getPriority().equals(currentP);
        }
    }
}