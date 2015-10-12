package datainterpreter.tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import datainterpreter.interpreter.DataInterpreter;

/**
 * Test class to test methods in the DataInterpreter.
 * 
 * NOTE: Please note that I chose to make these methods directly output a user
 * friendly message. If needed, I would happily change it to a format where it
 * just returned raw data (ints, ArrayList<Task>, etc).
 * 
 * @author jonathan
 *
 */
public class DataTests {
	DataInterpreter di;
	Date date;
	Date date2;

	/*
	 * I realize a large portion of this data here is deprecated, but it works
	 * for testing purposes.
	 */
	@SuppressWarnings("deprecation")
	@Before
	public void setUp() throws Exception {
		di = new DataInterpreter();
		date = new Date();
		date2 = new Date();
		date.setYear(115);
		date.setMonth(1);
		date.setDate(23);
		date.setMinutes(0);
		date.setHours(0);
		date.setSeconds(0);
		date2.setMonth(1);
		date2.setYear(115);
		date2.setDate(24);
		date2.setHours(0);
		date2.setMinutes(0);
		date2.setSeconds(0);
	}

	@Test
	public void testTasksUpToDate()
			throws FileNotFoundException, IOException, ParseException, java.text.ParseException {
		assertTrue(this.di.tasksUpToDate(this.date).equals("Task changes up to Mon Feb 23 00:00:00 CST 2015 - Open: 26, Closed: 613"));
		assertTrue(this.di.tasksUpToDate(null).equals("Incorrect date format!"));
	}

	@Test
	public void testTasksInDateRange() {
		assertEquals(this.di.tasksInDateRange(this.date, null), "Incorect date format!");
		assertEquals(this.di.tasksInDateRange(null, this.date2), "Incorect date format!");
		assertEquals(this.di.tasksInDateRange(null, null), "Incorect date format!");
		assertEquals(this.di.tasksInDateRange(this.date2, this.date), "The first date must be before the second date!");
		assertEquals(this.di.tasksInDateRange(this.date, this.date2),
				"Task changes between Mon Feb 23 00:00:00 CST 2015 and Tue Feb 24 00:00:00 CST 2015 - Open: 37, Closed: 11");
	}

	@Test
	public void testGetMostRecentTaskName() {
		assertEquals(this.di.getMostRecentTaskName(999), "There are no tasks with that instance ID.");
		assertEquals(this.di.getMostRecentTaskName(553),
				"Most recent task name for Instance ID 553: Review Coverage Branch Request");
		assertEquals(this.di.getMostRecentTaskName(491),
				"Most recent task name for Instance ID 491: Investigate Claim");
	}

	@Test
	public void testGetInstanceIdCount() {
		assertEquals(this.di.getInstanceIdCount(491), "There are 29 tasks with Instance ID 491");
		assertEquals(this.di.getInstanceIdCount(553), "There are 22 tasks with Instance ID 553");
		assertEquals(this.di.getInstanceIdCount(0), "There are no tasks with that instance ID.");
	}

	@Test
	public void testGetAssigneeTasks() {
		assertEquals(this.di.getAssigneeTasks("Impact 2014"), "Tasks for Impact 2014 - Open: 52, Closed: 611");
		assertEquals(this.di.getAssigneeTasks("dbailie"), "Tasks for dbailie - Open: 0, Closed: 18");
		assertEquals(this.di.getAssigneeTasks(""), "There are no tasks for that assignee.");
		assertEquals(this.di.getAssigneeTasks(null), "There are no tasks for that assignee.");
	}

}
