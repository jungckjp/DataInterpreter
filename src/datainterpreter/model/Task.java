package datainterpreter.model;

import java.util.Date;
import java.util.Map;

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
	public boolean initialized;
	
	
	public Task() {
		this.initialized = false;
	}
}