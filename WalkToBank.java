package scripts;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.script.Script;

public class WalkToBank implements Activity{
	private Script script;
	private Area bankarea = new Area(new Position(3250,3424,0),new Position(3257,3419,0));
	public String id = "WalkToBank";
	public WalkToBank(Script script){
		this.script = script;
	}
	public String getID(){
		return this.id;
	}
	public void run() throws InterruptedException{
		if(Script.random(1,5) > 4) {
			script.log("Taking random break");
			Script.sleep(Script.random(4000,7000));
		}
		script.walking.webWalk(new Position(Script.random(3250,3254),Script.random(3420,3422),0));
		if(!bankarea.contains(script.myPosition())){
			script.walking.webWalk(new Position(Script.random(3250,3254),Script.random(3420,3422),0));
		}
	}
	public boolean validate(){
		return !bankarea.contains(script.myPosition()) && !script.inventory.contains("Coins") && script.inventory.isFull() && script.inventory.getItems()[27].getId() == 1937;
	}
}
