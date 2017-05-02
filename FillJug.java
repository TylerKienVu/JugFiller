package scripts;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.ConditionalSleep;

public class FillJug implements Activity{
	private Script script;
	private Area fountainarea = new Area(new Position(3241,3437,0),new Position(3236,3432,0));
	public String id = "FillJug";
	public FillJug(Script script){
		this.script = script;
	}
	public String getID(){
		return this.id;
	}
	public void run() throws InterruptedException{
		RS2Object fountain = script.objects.closest("Fountain");
		script.camera.toTop();
		if(!script.inventory.isItemSelected() && !script.myPlayer().isMoving() && !script.myPlayer().isAnimating()){
			if(script.inventory.getItem("Jug").interact("Use")){
				new ConditionalSleep(5000){
					@Override
					public boolean condition() throws InterruptedException {
						return script.inventory.isItemSelected();
					}
				}.sleep();
			}
		}
		if(script.inventory.isItemSelected() && fountain != null){
			if(fountain.interact("Use")){
				new ConditionalSleep(20000){
					@Override
					public boolean condition() throws InterruptedException {
						return !script.inventory.contains("Jug");
					}
				}.sleep();
			}
		}
	}
	public boolean validate(){
		return fountainarea.contains(script.myPosition()) && script.inventory.contains(1935);
	}
}
