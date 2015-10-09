package datainterpreter.interpreter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import org.json.simple.parser.ParseException;

import datainterpreter.model.Task;

/**
 * Data Interpreter which does the main work for comparing data.
 * 
 * NOTE: Please note that I chose to make these methods directly output a user
 * friendly message. If needed, I would happily change it to a format where it
 * just returned raw data (ints, ArrayList<Task>, etc).
 * 
 * @author jonathan
 *
 */
public class DataInterpreter {
	public ArrayList<Task> tasks;
	public Map<Long, ArrayList<Task>> tasksByInstanceId;

	/*
	 * Set up the tasks ArrayList and Map by instance ID.
	 */
	public DataInterpreter() throws FileNotFoundException, IOException, ParseException, java.text.ParseException {
		String fileName = "data/task-3.json";
		Parser parser = new Parser();
		tasks = parser.parse(fileName);
		tasksByInstanceId = parser.getTasksByInstanceId();
	}

	/*
	 * Returns all tasks that have been opened and closed within a date range.
	 * If a task was opened and closed within the range, it will count for both.
	 */
	public String tasksInDateRange(Date date1, Date date2) {
		if (!(date1 != null && date2 != null)) {
			return "Incorect date format!";
		}
		if (date2.before(date1)) {
			return "The first date must be before the second date!";
		}
		int closed = 0;
		int open = 0;
		for (Task t : tasks) {
			if (t.closeDate != null) {
				if (t.closeDate.before(date2) && (t.closeDate.after(date1) || t.closeDate == date1)) {
					closed++;
				}
			}
			if (t.createDate.before(date2) && (t.createDate.after(date1) || t.createDate == date1)) {
				open++;
			}
		}
		return "Task changes between " + date1 + " and " + date2 + " - Open: " + open + ", Closed: " + closed;
	}

	/*
	 * Returns the number of tasks opened and closed as of the date. If it has
	 * been closed, it won't count as open.
	 */
	public String tasksUpToDate(Date date) {
		if (!(date != null)) {
			return "Incorrect date format!";
		}
		int closed = 0;
		int open = 0;
		for (Task t : tasks) {
			if (t.closeDate != null && (t.closeDate.before(date) || t.closeDate == date)) {
				closed++;
			} else if (t.createDate.before(date)) {
				open++;
			}
		}
		return "Task changes up to " + date + " - Open: " + open + ", Closed: " + closed;

	}

	/*
	 * Returns the name of the most recent (create date) task for that instance
	 * ID.
	 */
	public String getMostRecentTaskName(long instance) {
		ArrayList<Task> tasks;
		if ((tasks = tasksByInstanceId.get(instance)) != null) {
			Task mostRecentTask = tasks.get(0);
			for (Task t : tasks) {
				if (t.createDate.after(mostRecentTask.createDate)) {
					mostRecentTask = t;
				}
			}
			return "Most recent task name for Instance ID " + instance + ": " + mostRecentTask.name;
		} else {
			return "There are no tasks with that instance ID.";
		}

	}

	/*
	 * Returns the number of tasks by an instance ID.
	 */
	public String getInstanceIdCount(long instance) {
		ArrayList<Task> tasks;
		if ((tasks = tasksByInstanceId.get(instance)) != null) {
			int count = tasks.size();
			return "There are " + count + " tasks with Instance ID " + instance;
		} else {
			return "There are no tasks with that instance ID.";
		}
	}

	/*
	 * Returns the number of closed tasks and open tasks for an assignee.
	 */
	public String getAssigneeTasks(String assignee) {
		if (!(assignee != null)) {
			return "There are no tasks for that assignee.";
		}
		int closed = 0;
		int open = 0;
		for (Task t : tasks) {
			if (t.assignee.equals(assignee)) {
				if (t.closeDate != null) {
					closed++;
				} else {
					open++;
				}
			}
		}
		if (closed == 0 && open == 0) {
			return "There are no tasks for that assignee.";
		}
		return "Tasks for " + assignee + " - Open: " + open + ", Closed: " + closed;

	}

}
