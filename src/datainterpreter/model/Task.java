package datainterpreter.model;

import java.util.Date;
import java.util.Map;

/**
 * Data model object for tasks.
 * 
 * @author jonathan
 *
 */
public class Task {
	public String instanceName;
	public Date dueDate;
	public String priority;
	public Date closeDate;
	public String instanceStatus;
	public String asigneeType;
	public Date createDate;
	public String name;
	public String url;
	public String assignee;
	public long instanceId;
	public String status;
	public Map<String, Map<String, Object>> variables;
	public String processName;
	public String id;

	// This Task class is just a data object. It stores variables for searches.
	public Task() {
	}
}
