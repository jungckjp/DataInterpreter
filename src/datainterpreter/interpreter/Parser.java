package datainterpreter.interpreter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import datainterpreter.model.Task;

/**
 * This class parses the data from the JSON into the data objects used to track
 * information for BP3 based on a set of data. Heavy use of the org.json.simple
 * package exists here.
 * 
 * @author jonathan
 */
public class Parser {

	private Map<Long, ArrayList<Task>> tasksByInstanceId = new HashMap<Long, ArrayList<Task>>();

	@SuppressWarnings("unchecked")
	public ArrayList<Task> parse(String fileName)
			throws FileNotFoundException, IOException, ParseException, java.text.ParseException {
		JSONParser parser = new JSONParser();
		JSONArray entries = (JSONArray) parser.parse(new InputStreamReader(new FileInputStream(fileName)));

		ArrayList<Task> tasks = new ArrayList<Task>();

		for (Object e : entries) {
			try {
				JSONObject data = (JSONObject) e;
				Task task = new Task();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
				task.instanceId = (long) data.get("instanceId");
				task.dueDate = format.parse((String) data.get("dueDate"));
				task.priority = (String) data.get("priority");
				if (data.get("closeDate") != null) {
					task.closeDate = format.parse((String) data.get("closeDate"));
				}
				task.instanceStatus = (String) data.get("instanceStatus");
				task.asigneeType = (String) data.get("asigneeType");
				task.createDate = format.parse((String) data.get("createDate"));
				task.name = (String) data.get("name");
				task.url = (String) data.get("url");
				task.assignee = (String) data.get("assignee");
				task.instanceId = (long) data.get("instanceId");
				task.status = (String) data.get("status");
				task.variables = (Map<String, Map<String, Object>>) data.get("variables");
				task.processName = (String) data.get("processName");
				task.id = (String) data.get("id");

				if (tasksByInstanceId.containsKey(task.instanceId)) {
					tasksByInstanceId.get(task.instanceId).add(task);
				} else {
					ArrayList<Task> newTasks = new ArrayList<Task>();
					newTasks.add(task);
					tasksByInstanceId.put(task.instanceId, newTasks);
				}

				task.initialized = true;
				tasks.add(task);
			} catch (NullPointerException ex) {
				ex.printStackTrace();
			}
		}
		return tasks;
	}

	public Map<Long, ArrayList<Task>> getTasksByInstanceId() {
		return this.tasksByInstanceId;
	}

}
