package datainterpreter.interpreter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.json.simple.parser.ParseException;

import datainterpreter.model.Task;

public class DataInterpreter {
	public static ArrayList<Task> tasks;

	@SuppressWarnings("deprecation")
	public static void main(String[] args)
			throws ParseException, FileNotFoundException, IOException, java.text.ParseException {
		String fileName = "data/task-3.json";
		Parser parser = new Parser();
		tasks = parser.parse(fileName);
		Date date = new Date();
		date.setYear(115);
		date.setMonth(1);
		date.setDate(23);
		date.setMinutes(0);
		date.setHours(0);
		date.setSeconds(0);
		Date date2 = new Date();
		date2.setMonth(1);
		date2.setYear(115);
		date2.setDate(24);
		System.out.println(tasksUpToDate(date));
		System.out.println(tasksInDateRange(date, date2));
		System.out.println(getMostRecentTaskName(553));
		System.out.println(getInstanceIdCount(493));
		System.out.println(getAssigneeTasks("Impact 2014"));
		System.out.println(getAssigneeTasks("dbailie"));
	}

	public static String tasksInDateRange(Date date1, Date date2) {
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

	public static String tasksUpToDate(Date date) {
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
	
	public static String getMostRecentTaskName(long instance) {
		Task mostRecentTask = new Task();
		for (Task t : tasks) {
			if (t.instanceId == instance) {
				if (!mostRecentTask.initialized) {
					mostRecentTask = t;
					mostRecentTask.initialized = true;
				}
				if (t.createDate.after(mostRecentTask.createDate)) {
					mostRecentTask = t;
				}
			}
		}
		return "Most recent task name for Instance ID " + instance + ": " + mostRecentTask.name;
	}
	
	public static String getInstanceIdCount(long instance) {
		int count = 0;
		for (Task t : tasks) {
			if (t.instanceId == instance) {
				count++;
			}
		}
		return "There are " + count + " tasks with Instance ID " + instance;
	}
	
	public static String getAssigneeTasks(String assignee) {
		int closed = 0;
		int open = 0;
		for (Task t : tasks) {
			if (t.assignee.equals(assignee)) {
				if (t.closeDate != null) {
					closed ++;
				} else {
					open++;
				}
			}
		}
		return "Tasks for " + assignee + " - Open: " + open + ", Closed: " + closed;

	}

}
