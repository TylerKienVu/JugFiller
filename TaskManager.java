package scripts;

import java.util.ArrayList;
import org.osbot.rs07.script.Script;

public class TaskManager {
	private Activity activeTask;
	private ArrayList<Activity> taskList;
	private Script script;

	public TaskManager(ArrayList<Activity> taskList, Script script)	{
		this.taskList = taskList;
		this.script = script;
	}
	
	public Activity getActive() throws InterruptedException	{
		/*
		if(this.activeTask != null && (this.activeTask.getID().equals("AutoMule") || this.activeTask.getID().equals("GetMoney"))){
			this.activeTask = taskList.get(2);
			script.log("Changing Task to: " + activeTask.getID());
		}
		*/
		if(this.activeTask == null || !this.activeTask.validate())	{
			for (Activity task: taskList)	{
				if (task != null && task.validate())	{
					script.log("Changing Task to: " + task.getID());
					this.activeTask = task;
					break;
				}
			}
		}		
		return this.activeTask;
	}
}