package scripts;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.script.Script;

public class WalkToFountain implements Activity{
	private Script script;
	private Area fountainarea = new Area(new Position(3241,3437,0),new Position(3236,3432,0));
	public String id = "WalkToFountain";
	
	public WalkToFountain(Script script){
		this.script = script;
	}
	public String getID(){
		return this.id;
	}
	public void run(){
		script.walking.webWalk(new Position(Script.random(3236,3241),Script.random(3432,3437),0));
	}
	public boolean validate(){
		return !fountainarea.contains(script.myPosition()) && !script.inventory.contains("Coins") && script.inventory.contains(1935);
	}

}
