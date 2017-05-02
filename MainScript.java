package scripts;

import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

@ScriptManifest(author = "Tylersobored", info = "Jug Filling Script", name = "jugfiller", version = 0, logo = "")

public class MainScript extends Script {
	private ArrayList<Activity> activities = new ArrayList<Activity>();
	private TaskManager taskmanager;
	
	@Override
	public void onStart() throws InterruptedException {
		Collections.addAll(activities, new GetMoney(this),new AutoMule(this),new OpenBank(this),new WalkToStore(this), new BuyJugs(this), new WalkToBankFromStore(this),new WithdrawJugs(this),new WalkToFountain(this), new FillJug(this), new WalkToBank(this),new Wait(this));
		this.taskmanager = new TaskManager(activities,this);
		worlds.hop(394);
		Script.sleep(10000);
		log("Finished after world hop security sleep");
		this.walking.webWalk(new Position(3253,3420,0));
	}
	
	@Override
	public int onLoop() throws InterruptedException {
		taskmanager.getActive().run();
		return Script.random(500, 700);
	}
	
	@Override
	public void onExit() {
	}
	
	@Override
	public void onPaint(Graphics2D g) {
		
	}
}