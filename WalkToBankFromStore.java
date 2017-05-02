package scripts;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.script.Script;

public class WalkToBankFromStore implements Activity{
	private Script script;
	private Area bankarea = new Area(new Position(3250,3424,0),new Position(3257,3419,0));
	public String id = "WalkToBankFromStore";
	public WalkToBankFromStore(Script script){
		this.script = script;
	}
	public String getID(){
		return this.id;
	}
	public void run(){
		if(script.worlds.getCurrentWorld() != 394){
			script.worlds.hop(394);
		}
		script.walking.webWalk(new Position(Script.random(3250,3254),Script.random(3420,3422),0));
		if(!bankarea.contains(script.myPosition())){
			script.walking.webWalk(new Position(Script.random(3250,3254),Script.random(3420,3422),0));
		}
	}
	public boolean validate(){
		return script.inventory.getAmount("Jug") > 1500 && !bankarea.contains(script.myPosition());
	}
}
